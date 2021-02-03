package com.holcvart.androidptut.view.fragment;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.holcvart.androidptut.R;

public class CustomBackPressed extends OnBackPressedCallback {

    private Fragment fragment;
    private int destination;
    public CustomBackPressed(boolean enabled, Fragment fragment, int destination) {
        super(enabled);
        this.fragment = fragment;
        this.destination = destination;
    }

    @Override
    public void handleOnBackPressed() {
        NavHostFragment.findNavController(fragment).navigate(destination);
    }
}
