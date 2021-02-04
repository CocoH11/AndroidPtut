package com.holcvart.androidptut.view.recycler_view;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Intervention;

public class EstimateRecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewDate;

    public EstimateRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = (TextView)itemView.findViewById(R.id.textViewTitleEstimate);
        textViewDate = (TextView)itemView.findViewById(R.id.textViewDateEstimate);
        textViewDescription = (TextView)itemView.findViewById(R.id.textViewDescriptionEstimate);
    }

    public void setDetails(Intervention intervention){
        textViewTitle.setText(intervention.getTitle());
        textViewDate.setText(intervention.getDate());
        textViewDescription.setText(intervention.getDescription());
    }

}
