package com.enzo.testaufgabe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enzo.testaufgabe.models.Message;

import java.util.List;

/**
 * Created by enzo on 12.04.18.
 */

public class MessagesViewAdapter extends RecyclerView.Adapter<MessagesViewHolder> {
    private List<Message> messages;
    private Context context;

    public MessagesViewAdapter(Context context, List<Message> messages) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public MessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_message_item, parent, false);
        MessagesViewHolder viewHolder = new MessagesViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MessagesViewHolder holder, final int position) {
        Message message = messages.get(position);
        holder.titleView.setText(message.getTitle());
        holder.bodyView.setText(message.getBody());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
