package com.holcvart.androidptut.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.view.model.EstimateDetailsViewModel;

public class EstimateDetailsFragment extends Fragment implements View.OnClickListener, Observer<Intervention> {
    private EstimateDetailsViewModel mViewModel;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewClientName;
    private TextView textViewClientFirstName;
    private Intervention intervention;
    private ActionBar actionBar;
    private FloatingActionButton floatingActionButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        customizeBackNavigation();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                NavHostFragment.findNavController(this).navigate(R.id.action_nav_estimate_details_to_nav_estimate);
                break;
        }
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(EstimateDetailsViewModel.class);
        return inflater.inflate(R.layout.estimate_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        floatingActionButton = ((MainActivity)requireActivity()).getFloatingActionButton();
        actionBar = ((MainActivity)requireActivity()).getSupportActionBar();
        customizeFloatingActionButton();

    }

    private void customizeFloatingActionButton(){
        floatingActionButton.setOnClickListener(this);
    }

    private void customizeActionBar(){
        actionBar.setTitle(intervention.getTitle());
        actionBar.setSubtitle(intervention.getDate());
    }

    private void customizeBackNavigation(){
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new CustomBackPressed(true, this, R.id.action_nav_estimate_details_to_nav_estimate));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == floatingActionButton.getId()){
            navToUpdateView(getView());
        }
    }

    public void navToUpdateView(View view){
        long id = intervention.getId();
        Bundle bundle = new Bundle();
        bundle.putLong("interventionId", id);
        NavHostFragment.findNavController(this).navigate(R.id.action_nav_estimate_details_to_nav_estimate_create, bundle);
    }

    @Override
    public void onChanged(Intervention intervention) {
        if (this.intervention == null)this.intervention = intervention;
        customizeActionBar();
    }
}