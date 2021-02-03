package com.holcvart.androidptut.view.fragment;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.view.model.InterventionViewModel;
import com.holcvart.androidptut.view.recycler_view.InterventionListAdapter;

import java.util.List;

public class InterventionFragment extends Fragment implements View.OnClickListener, Observer<List<Intervention>>{

    private InterventionViewModel mViewModel;
    private RecyclerView recyclerView;
    private List<Intervention> mInterventions;
    private FloatingActionButton floatingActionButton;
    private ActionBar actionBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(InterventionViewModel.class);
        floatingActionButton = ((MainActivity)requireActivity()).getFloatingActionButton();
        actionBar = ((MainActivity)requireActivity()).getSupportActionBar();
        customizeFloatingActionButton();
        customizeActionBar();
        View root = inflater.inflate(R.layout.intervention_fragment, container, false);
        recyclerView = root.findViewById(R.id.interventionRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        mViewModel.getInterventions().observe(getViewLifecycleOwner(), this);
        return root;
    }

    private void customizeActionBar(){
        actionBar.setSubtitle(null);
    }

    private void customizeFloatingActionButton(){
        floatingActionButton.setImageResource(android.R.drawable.ic_input_add);
        floatingActionButton.setOnClickListener(this);
    }

    public void navToDetailedView(View view, int position){
        long id= Long.parseLong(String.valueOf(recyclerView.getAdapter().getItemId(position)));
        Bundle bundle = new Bundle();
        bundle.putLong("interventionId", id);
        Navigation.findNavController(view).navigate(R.id.action_nav_intervention_to_intervention_details, bundle);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == floatingActionButton.getId()){
            Navigation.findNavController(getView()).navigate(R.id.action_nav_intervention_to_nav_intervention_create);
        }
    }

    @Override
    public void onChanged(List<Intervention> interventions) {
        if(mInterventions == null)mInterventions = interventions;
        recyclerView.setAdapter(new InterventionListAdapter(mInterventions, this));
    }
}