package com.holcvart.androidptut.view.recycler_view;

import android.util.Log;
import android.view.View;

import com.holcvart.androidptut.view.fragment.ClientFragment;

public class ClientItemOnClickListener implements View.OnClickListener {
    private int position;
    private ClientFragment clientFragment;

    public ClientItemOnClickListener(int position, ClientFragment clientFragment) {
        this.position = position;
        this.clientFragment = clientFragment;
    }

    @Override
    public void onClick(View v) {
        clientFragment.navToDetailedView(v, position);
    }
}
