package com.enzo.testaufgabe;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by enzo on 12.04.18.
 */

public class MessagesViewHolder extends RecyclerView.ViewHolder {
    View view;
    TextView titleView;
    TextView bodyView;

    MessagesViewHolder(View v) {
        super(v);
        this.view = v;
        titleView = v.findViewById(R.id.message_title);
        bodyView = v.findViewById(R.id.message_body);
    }
}