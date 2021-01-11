package com.holcvart.androidptut.view.recycler_view;

import android.view.View;

import com.holcvart.androidptut.view.fragment.InterventionFragment;

public class InterventionItemOnClickListener implements View.OnClickListener {
    private int position;
    private InterventionFragment interventionFragment;

    public InterventionItemOnClickListener(int position, InterventionFragment interventionFragment){
        this.position = position;
        this.interventionFragment = interventionFragment;
    }
    @Override
    public void onClick(View v) {
        interventionFragment.navToDetailedView(v, position);
    }
}
