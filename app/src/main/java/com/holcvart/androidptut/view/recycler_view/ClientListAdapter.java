package com.holcvart.androidptut.view.recycler_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.view.fragment.ClientFragment;

import java.util.List;

public class ClientListAdapter extends RecyclerView.Adapter {
    private List<Client> clients;
    private ClientFragment clientFragment;

    public ClientListAdapter(List<Client> clients, ClientFragment clientFragment) {
        this.clients = clients;
        this.clientFragment=clientFragment;
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
        ((ClientRecyclerViewHolder)holder).itemView.setOnClickListener(new ClientItemOnClickListener(position, clientFragment));

    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.client_item;
    }

    @Override
    public long getItemId(int position) {
        return clients.get(position).getId();
    }
}
