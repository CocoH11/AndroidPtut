package com.holcvart.androidptut.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.view.model.EstimateViewModel;

public class EstimateFragment extends Fragment {

    private EstimateViewModel estimateViewModel;
    private FloatingActionButton floatingActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        estimateViewModel = new ViewModelProvider(this).get(EstimateViewModel.class);
        floatingActionButton = ((MainActivity)requireActivity()).getFloatingActionButton();
        View root = inflater.inflate(R.layout.fragment_estimate, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        estimateViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private void customizeFloatingActionButton(){
        floatingActionButton.setImageResource(android.R.drawable.ic_input_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Replace with your own action", (int) 2).show();
            }
        });
    }
}