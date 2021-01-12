package com.holcvart.androidptut.view.recycler_view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Client;

public class ClientRecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewName;
    private TextView textViewFirstName;

    public ClientRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewName=(TextView)itemView.findViewById(R.id.textViewName);
        textViewFirstName=(TextView)itemView.findViewById(R.id.textViewFirstName);
    }

    public void setDetails(Client client){
        textViewFirstName.setText(client.getFirstName());
        textViewName.setText(client.getName());
    }
}
