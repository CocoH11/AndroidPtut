package com.holcvart.androidptut.view.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.view.model.EstimateViewModel;
import com.holcvart.androidptut.view.model.InterventionViewModel;
import com.holcvart.androidptut.view.recycler_view.EstimateListAdapter;
import com.holcvart.androidptut.view.recycler_view.InterventionListAdapter;

import java.util.List;

public class EstimateFragment extends Fragment implements View.OnClickListener, Observer<List<Intervention>> {

    private EstimateViewModel mViewModel;
    private RecyclerView recyclerView;
    private List<Intervention> mInterventions;
    private FloatingActionButton floatingActionButton;
    private ActionBar actionBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(EstimateViewModel.class);
        floatingActionButton = ((MainActivity)requireActivity()).getFloatingActionButton();
        actionBar = ((MainActivity)requireActivity()).getSupportActionBar();
        customizeFloatingActionButton();
        customizeActionBar();
        View root = inflater.inflate(R.layout.fragment_estimate, container, false);
        recyclerView = root.findViewById(R.id.estimateRecyclerView);
        recyclerView.setHasFixedSize(true);
        onConfigurationChanged(getActivity().getResources().getConfiguration());
        mViewModel.getInterventions().observe(getViewLifecycleOwner(), this);
        return root;
    }

    public void customizeFloatingActionButton(){
        floatingActionButton.setImageResource(android.R.drawable.ic_input_add);
        floatingActionButton.setOnClickListener(this);
        floatingActionButton.show();
    }

    public void customizeActionBar(){
        actionBar.setSubtitle(null);
    }

    public void navToDetailedView(View view, int position){
        long id = Long.parseLong(String.valueOf(recyclerView.getAdapter().getItemId(position)));
        Bundle bundle = new Bundle();
        bundle.putLong("interventionId", id);
        NavHostFragment.findNavController(this).navigate(R.id.action_nav_estimate_to_nav_estimate_details, bundle);
    }

    public void validateEstimation(int position){
        mViewModel.validate(position, recyclerView.getAdapter().getItemId(position));
        mViewModel.reloadInterventions();
    }

    @Override
    public void onChanged(List<Intervention> interventions) {
        if(mInterventions == null)mInterventions = interventions;
        recyclerView.setAdapter(new EstimateListAdapter(mInterventions, this));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == floatingActionButton.getId())NavHostFragment.findNavController(this).navigate(R.id.action_nav_estimate_to_nav_estimate_create);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }
}