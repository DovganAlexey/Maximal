package com.enzo.testaufgabe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enzo.testaufgabe.models.Person;

import java.util.List;

/**
 * Created by enzo on 11.04.18.
 */

public class MainFragRecyclerAdapter extends RecyclerView.Adapter<MainFragViewHolder> {
    private List<Person> items;
    private Context context;

    public MainFragRecyclerAdapter(Context context, List<Person> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public MainFragViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_fragment_list_item, parent, false);
        MainFragViewHolder viewHolder = new MainFragViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MainFragViewHolder holder, final int position) {
        final Person person = items.get(position);
        holder.nameView.setText(person.getUsername());
        holder.emailView.setText(person.getEmail());
        holder.companyName.setText(person.getCompany().getCompanyName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemSelected(person);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void onItemSelected(Person person) {
        if (((MainActivity) context).isTablet()) {
            ((MainActivity) context).personFragment.updatePersonFragment(person);
        } else {
            // create and show PersonFragment
            PersonFragment itemFragment = new PersonFragment();
            itemFragment.setPerson(person);
            ((MainActivity) context).getFrManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, itemFragment, person.getUsername())
                    .addToBackStack(null)
                    .commit();
            ((MainActivity) context).setToolbarTittle(person.getUsername());
        }
    }
}
