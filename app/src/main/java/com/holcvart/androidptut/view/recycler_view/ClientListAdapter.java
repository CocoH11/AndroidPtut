package com.holcvart.androidptut.view.recycler_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Client;

import java.util.List;

public class ClientListAdapter extends RecyclerView.Adapter {
    private List<Client> clients;

    public ClientListAdapter(List<Client> clients) {
        this.clients = clients;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ClientRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ClientRecyclerViewHolder)holder).setDetails(clients.get(position));

    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.client_item;
    }


}
