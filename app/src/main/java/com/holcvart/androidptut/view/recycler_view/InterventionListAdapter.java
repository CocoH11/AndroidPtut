package com.holcvart.androidptut.view.recycler_view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.view.fragment.ClientFragment;
import com.holcvart.androidptut.view.fragment.InterventionFragment;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;

public class InterventionListAdapter extends Adapter {

    private List<Entity> interventions;
    private InterventionFragment interventionFragment;

    public InterventionListAdapter(List<Entity> interventions, InterventionFragment interventionFragment) {
        this.interventions = interventions;
        this.interventionFragment=interventionFragment;
        Log.d("intervention title", ((Intervention)interventions.get(0)).getTitle());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new InterventionRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((InterventionRecyclerViewHolder)holder).setDetails((Intervention)interventions.get(position));
        ((InterventionRecyclerViewHolder)holder).itemView.setOnClickListener(new InterventionItemOnClickListener(position, interventionFragment));
    }

    @Override
    public int getItemCount() {
        return interventions.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.intervention_item;
    }

    @Override
    public long getItemId(int position) {
        return interventions.get(position).getId();
    }
}
