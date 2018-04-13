package com.enzo.testaufgabe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enzo.testaufgabe.models.Message;
import com.enzo.testaufgabe.models.Person;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by enzo on 11.04.18.
 */

public class PersonFragment extends Fragment {

    private ArrayList<Message> messages = new ArrayList<>();
    private RecyclerView messagesView;
    private MessagesViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    public RelativeLayout progressBar;
    private View fragmentView;
    public final static String STATE_PERSON = "state_person";
    //
    TextView nameView;
    TextView emailView;
    TextView phoneView;
    TextView websiteView;
    TextView companyView;
    TextView catchPhraseView;
    TextView companyBSView;
    TextView fullAddressView;
    TextView zipcodeView;
    TextView coordinatesView;
    private Person person;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getting saved data after recreating
        if (savedInstanceState != null) {
            person = (Person) savedInstanceState.getSerializable(STATE_PERSON);
        }
    }

    public void setPerson(Person person) {
        this.person = person;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.person_detail_fragment, container, false);
        messagesView = fragmentView.findViewById(R.id.messages_view);
        progressBar = fragmentView.findViewById(R.id.loading_spinner);
        mLayoutManager = new LinearLayoutManager(getContext()) {
            // experimental stopping scroll at recycler view
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        messagesView.setLayoutManager(mLayoutManager);
        mAdapter = new MessagesViewAdapter(getContext(), messages);
        messagesView.setAdapter(mAdapter);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameView = view.findViewById(R.id.name);
        emailView = view.findViewById(R.id.email);
        phoneView = view.findViewById(R.id.phone);
        websiteView = view.findViewById(R.id.website);
        companyView = view.findViewById(R.id.company_name);
        catchPhraseView = view.findViewById(R.id.catchPhrase);
        companyBSView = view.findViewById(R.id.business_services);
        fullAddressView = view.findViewById(R.id.full_address);
        zipcodeView = view.findViewById(R.id.zipcode);
        coordinatesView = view.findViewById(R.id.coordinates);
        updatePersonFragment(person);
    }

    public void updatePersonFragment(Person person) {
        if (person != null) {
            fragmentView.setVisibility(View.VISIBLE);
            this.person = person;
            try {
                if (((MainActivity) getContext()).isTablet())
                    nameView.setText(person.getUsername() + " - " + person.getName());
                else
                    nameView.setText(person.getName());
                emailView.setText(" : " + person.getEmail());
                phoneView.setText(" : " + person.getPhone());
                websiteView.setText(" : " + person.getWebsite());
                companyView.setText(person.getCompany().getCompanyName());
                catchPhraseView.setText(getResources().getString(R.string.catchPhrase) + person.getCompany().getCatchPhrase());
                companyBSView.setText(getResources().getString(R.string.business_services) + person.getCompany().getBusinessServices());
                fullAddressView.setText(person.getAddress().getFullAddress());
                zipcodeView.setText(" : " + person.getAddress().getZipcode());
                coordinatesView.setText(" : " + person.getAddress().getCoordinates());
                messages.clear();
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.VISIBLE);

                CustomApplication.getApi().getMessages(person.getId()).enqueue(new Callback<List<Message>>() {
                    @Override
                    public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                        try {
                            messages.addAll(response.body());
                        } catch (Exception ex) {
                            System.out.println("EXC: " + ex.toString());
                        }
                        mAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<Message>> call, Throwable t) {
                        // Ignore
                    }
                });
            } catch (Exception ex) {
                // Ignore
            }
        } else {
            fragmentView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        // saving current data
        state.putSerializable(STATE_PERSON, person);
    }
}