package com.holcvart.androidptut.view.recycler_view;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.holcvart.androidptut.view.fragment.EstimateFragment;
import com.holcvart.androidptut.view.fragment.InterventionFragment;

public class EstimateItemOnClickListener implements View.OnClickListener {
    private int position;
    private EstimateFragment estimateFragment;

    public EstimateItemOnClickListener(int position, EstimateFragment estimateFragment){
        this.position = position;
        this.estimateFragment = estimateFragment;
    }
    @Override
    public void onClick(View v) {
        estimateFragment.navToDetailedView(v, position);
    }
}
