package com.holcvart.androidptut.view.fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.view.model.ClientDetailViewModel;
import com.holcvart.androidptut.view.model.InterventionDetailsViewModel;
import com.holcvart.androidptut.view.recycler_view.InterventionListAdapter;
import com.holcvart.androidptut.view.recycler_view.PartsListAdapter;

import java.util.List;

public class InterventionDetailsFragment extends Fragment implements View.OnClickListener , Observer<Intervention> {
    private InterventionDetailsViewModel interventionDetailsViewModel;
    private TextView textViewDescription;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private ActionBar actionBar;
    private Intervention mIntervention;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        customizeBackNavigation();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Navigation.findNavController(getView()).navigate(R.id.action_nav_intervention_details_to_nav_intervention);
                break;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        interventionDetailsViewModel = new ViewModelProvider(this).get(InterventionDetailsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_intervention_details, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        floatingActionButton = ((MainActivity)requireActivity()).getFloatingActionButton();
        actionBar = ((MainActivity)requireActivity()).getSupportActionBar();
        textViewDescription=(TextView)view.findViewById(R.id.textViewInterventionDetailsDescription);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewPartsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        customizeFloatingActionButton();
        interventionDetailsViewModel.getIntervention(getArguments().getLong("interventionId")).observe(getViewLifecycleOwner(), this);
    }

    private void customizeFloatingActionButton(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navToUpdateView(getView());
            }
        });
    }

    private void customizeActionBar(){
        actionBar.setTitle(mIntervention.getTitle());
        actionBar.setSubtitle(mIntervention.getDate());
    }

    private void customizeBackNavigation(){
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(getInterventionDetailsFragment()).navigate(R.id.action_nav_intervention_details_to_nav_intervention);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onClick(View v) {

    }

    public void navToUpdateView(View view) {
        long id= mIntervention.getId();
        Bundle bundle = new Bundle();
        bundle.putLong("interventionId", id);
        Navigation.findNavController(view).navigate(R.id.action_nav_intervention_details_to_nav_intervention_create, bundle);
    }

    @Override
    public void onChanged(Intervention intervention) {
        if (mIntervention == null)mIntervention = intervention;
        customizeActionBar();
        System.out.println(mIntervention.getTitle());
        textViewDescription.setText(mIntervention.getDescription());
        recyclerView.setAdapter(new PartsListAdapter(mIntervention.getPartsNeededs()));
    }

    public InterventionDetailsFragment getInterventionDetailsFragment(){
        return this;
    }
}