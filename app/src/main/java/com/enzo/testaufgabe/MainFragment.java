package com.enzo.testaufgabe;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.enzo.testaufgabe.models.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by enzo on 11.04.18.
 */

public class MainFragment extends Fragment {
    private MainFragRecyclerAdapter mAdapter;
    private SwipeRefreshLayout swipeToRefresh;
    public ArrayList<Person> users = new ArrayList<>();
    public final static String STATE_ITEMS = "state_items";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getting saved data after recreating
        if (savedInstanceState != null) {
            users = (ArrayList<Person>) savedInstanceState.getSerializable(STATE_ITEMS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.main_fragment, container, false);
        swipeToRefresh = fragmentView.findViewById(R.id.swipe_to_refresh);
        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsersData();
            }
        });
        // RecyclerView
        RecyclerView mRecyclerView = fragmentView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MainFragRecyclerAdapter(getContext(), users);
        mRecyclerView.setAdapter(mAdapter);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // no request if data is there
        if (users.isEmpty()) {
            getUsersData();
        }
    }

    private void getUsersData() {
        swipeToRefresh.setRefreshing(true);
        if (checkForNetwork()) {
            CustomApplication.getApi().getUsers().enqueue(new Callback<List<Person>>() {
                @Override
                public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                    try {
                        ArrayList<Person> persons = new ArrayList<>();
                        persons.addAll(response.body());
                        handleFreshData(persons);
                    } catch (Exception ex) {
                        // if there is some parse problem getting data from db
                        getDBData("Can not properly parse data!");
                    }
                }

                @Override
                public void onFailure(Call<List<Person>> call, Throwable t) {
                    // if server not responding getting data from db
                    getDBData("Server is not reachable!");
                }
            });
        } else {
            // offline getting data from db
            getDBData("You are offline!");
        }
    }

    private void getDBData(String reason) {
        if (users.isEmpty()) {
            // case : 1. app-start 2. can not get server data
            users.addAll(CustomApplication.getDBHelper().getAllUsers());
            // update UI
            mAdapter.notifyDataSetChanged();
        }
        swipeToRefresh.setRefreshing(false);
        // showing cause of connection fail
        Toast.makeText(getContext(), reason, Toast.LENGTH_LONG).show();
    }

    private boolean checkForNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void handleFreshData(ArrayList<Person> persons) {
        int savedHash = CustomApplication.getSharedPrefs().getInt("users_hash", 0);
        int newHash = persons.hashCode();

        // updating db if there is difference between fresh response and db-data
        System.out.println("CHECKING HASHES EQUALS " + String.valueOf(savedHash == newHash));
        if (savedHash != newHash) {
            // sort by username
            Collections.sort(persons, new Comparator<Person>() {
                public int compare(Person v1, Person v2) {
                    return v1.getUsername().compareTo(v2.getUsername());
                }
            });
            // updating UI
            users.clear();
            users.addAll(persons);
            mAdapter.notifyDataSetChanged();
            // TODO thread for db and shared manipulations
            // fill db with fresh data
            DBHelper db = CustomApplication.getDBHelper();
            db.dropTable();
            for (Person person : users) {
                db.addUser(person);
            }
            // saving hash of new data
            SharedPreferences.Editor editor = CustomApplication.getSharedPrefs().edit();
            editor.putInt("users_hash", newHash);
            editor.commit();
            /////////////////////////
        } else if (users.isEmpty()) {
            // case : 1.app-start 2.saved data is actual
            // update UI
            // sort by username
            Collections.sort(persons, new Comparator<Person>() {
                public int compare(Person v1, Person v2) {
                    return v1.getUsername().compareTo(v2.getUsername());
                }
            });
            users.addAll(persons);
            mAdapter.notifyDataSetChanged();
        }
        swipeToRefresh.setRefreshing(false);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        // saving current data
        state.putSerializable(STATE_ITEMS, users);
    }
}