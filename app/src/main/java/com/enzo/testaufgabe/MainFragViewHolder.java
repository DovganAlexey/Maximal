package com.enzo.testaufgabe;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by enzo on 11.04.18.
 */

public class MainFragViewHolder extends RecyclerView.ViewHolder {
    View view;
    TextView nameView;
    TextView emailView;
    TextView companyName;

    MainFragViewHolder(View v) {
        super(v);
        this.view = v;
        nameView = v.findViewById(R.id.name);
        emailView = v.findViewById(R.id.email);
        companyName = v.findViewById(R.id.company_name);
    }
}