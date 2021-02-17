package com.holcvart.androidptut.view.recycler_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.view.fragment.EstimateFragment;
import com.holcvart.androidptut.view.fragment.InterventionFragment;
import com.holcvart.androidptut.view.model.EstimateDetailsViewModel;

import java.util.List;

public class EstimateListAdapter extends RecyclerView.Adapter{
    private List<Intervention> interventions;
    private EstimateFragment estimateFragment;

    public EstimateListAdapter(List<Intervention> interventions, EstimateFragment estimateFragment) {
        this.interventions = interventions;
        this.estimateFragment = estimateFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new EstimateRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((EstimateRecyclerViewHolder)holder).setDetails(interventions.get(position));
        View.OnClickListener clickedListener = new EstimateItemOnClickListener(position, estimateFragment);
        ((EstimateRecyclerViewHolder)holder).itemView.setOnClickListener(clickedListener);
        ((EstimateRecyclerViewHolder)holder).itemView.findViewById(R.id.buttonEstimateValidate).setOnClickListener(clickedListener);
    }

    @Override
    public int getItemCount() {
        return interventions.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.estimate_item;
    }

    @Override
    public long getItemId(int position) {
        return interventions.get(position).getId();
    }
}
