package com.holcvart.androidptut.view.recycler_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.PartsNeeded;

import java.util.List;

public class PartsListAdapter extends RecyclerView.Adapter {
    private List<PartsNeeded> partsNeededList;
    public PartsListAdapter(List<PartsNeeded> partsNeededList) {
        this.partsNeededList = partsNeededList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new PartRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        System.out.println(partsNeededList.get(position).getPart().getNaming());
        System.out.println(partsNeededList.get(position).getQuantity());
        ((PartRecyclerViewHolder)holder).setDetails(partsNeededList.get(position).getPart().getNaming(), partsNeededList.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
       return partsNeededList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_part;
    }

    @Override
    public long getItemId(int position) {
        return partsNeededList.get(position).getId();
    }
}
