package com.holcvart.androidptut.view.recycler_view;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.holcvart.androidptut.R;

public class PartRecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewPartNaming;
    private TextView textViewPartQuantity;
    public PartRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewPartNaming = (TextView) itemView.findViewById(R.id.textViewNaming);
        textViewPartQuantity = (TextView) itemView.findViewById(R.id.textViewQuantity);
    }

    public void setDetails(String naming, int quantity){
        System.out.println("naming: " + naming);
        System.out.println("quantity " + quantity);
        textViewPartNaming.setText(naming);
        textViewPartQuantity.setText(String.valueOf(quantity));
    }
}
