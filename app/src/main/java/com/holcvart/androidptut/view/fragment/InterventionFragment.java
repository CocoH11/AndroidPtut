package com.holcvart.androidptut.view.fragment;

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

import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.repository.InterventionRepository;
import com.holcvart.androidptut.view.model.CustomViewModelFactory;
import com.holcvart.androidptut.view.model.InterventionViewModel;
import com.holcvart.androidptut.view.recycler_view.InterventionListAdapter;

public class InterventionFragment extends Fragment {

    private InterventionViewModel mViewModel;
    private RecyclerView recyclerView;

    public static InterventionFragment newInstance() {
        return new InterventionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CustomViewModelFactory viewModelFactory= new CustomViewModelFactory(new InterventionRepository(((MainActivity)requireActivity()).getDatabase()));
        mViewModel = new ViewModelProvider(this, viewModelFactory).get(InterventionViewModel.class);
        View root = inflater.inflate(R.layout.intervention_fragment, container, false);
        recyclerView = root.findViewById(R.id.interventionRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        recyclerView.setAdapter(new InterventionListAdapter(mViewModel.findAll(), this));
        return root;
    }

    public void navToDetailedView(View view, int position){
        long id= Long.parseLong(String.valueOf(recyclerView.getAdapter().getItemId(position)));
        Bundle bundle = new Bundle();
        bundle.putLong("interventionId", id);
        Navigation.findNavController(view).navigate(R.id.action_nav_intervention_to_intervention_details, bundle);
    }

}