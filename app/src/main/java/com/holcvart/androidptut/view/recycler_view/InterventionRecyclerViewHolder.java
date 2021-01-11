package com.holcvart.androidptut.view.recycler_view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Intervention;

public class InterventionRecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewDate;
    public InterventionRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = (TextView)itemView.findViewById(R.id.textViewTitle);
        textViewDate = (TextView)itemView.findViewById(R.id.textViewDate);
        textViewDescription = (TextView)itemView.findViewById(R.id.textViewDescription);
    }

    public void setDetails(Intervention intervention){
        textViewTitle.setText(intervention.getTitle());
        textViewDate.setText(intervention.getDate());
        textViewDescription.setText(intervention.getDescription());
    }
}
