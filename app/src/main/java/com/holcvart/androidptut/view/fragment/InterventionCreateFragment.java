package com.holcvart.androidptut.view.fragment;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.view.model.ClientCreateViewModel;
import com.holcvart.androidptut.view.model.InterventionCreateViewModel;

import java.util.List;

public class InterventionCreateFragment extends Fragment implements Observer<Intervention> {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextDate;
    private Spinner clientSpinner;

    private InterventionCreateViewModel mViewModel;
    private FloatingActionButton floatingActionButton;
    private ActionBar actionBar;
    private Intervention mIntervention;
    private List<Client> mClients;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        actionBar = ((MainActivity)requireActivity()).getSupportActionBar();
        floatingActionButton = ((MainActivity)requireActivity()).getFloatingActionButton();
        mViewModel = new ViewModelProvider(this).get(InterventionCreateViewModel.class);
        return inflater.inflate(R.layout.intervention_create_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        customizeActionBar();
        customizeFloatingActionButton();
        editTextTitle = (EditText)view.findViewById(R.id.editTextTitle);
        editTextDescription = (EditText)view.findViewById(R.id.editTextDescription);
        editTextDate = (EditText)view.findViewById(R.id.editTextDate);
        clientSpinner = (Spinner)view.findViewById(R.id.spinnerClient);
        mViewModel.getClients().observe(getViewLifecycleOwner(), new Observer<List<Client>>() {
            @Override
            public void onChanged(List<Client> clients) {
                if (mClients == null)mClients = clients;
                ArrayAdapter adapter = new SpinnerClientAdapter(getContext(), R.layout.adapter_item_client, clients);
                clientSpinner.setAdapter(adapter);
            }
        });
        if (getArguments() != null)mViewModel.getIntervention(getArguments().getLong("interventionId")).observe(getViewLifecycleOwner(), this);
        else mViewModel.getIntervention().observe(getViewLifecycleOwner(), this);
    }

    private void customizeActionBar(){
        actionBar.setTitle("Nouvelle intervention");
    }
    private void customizeFloatingActionButton(){
        floatingActionButton.setImageResource(android.R.drawable.ic_media_next);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntervention.setTitle(editTextTitle.getText().toString());
                mIntervention.setDescription(editTextDescription.getText().toString());
                mIntervention.setDate(editTextDate.getText().toString());
                mViewModel.insertOrUpdate();
                navToDetailedView(getView());
            }
        });
    }

    public void navToDetailedView(View view) {
        long id= mIntervention.getId();
        Bundle bundle = new Bundle();
        bundle.putLong("interventionId", id);
        Navigation.findNavController(view).navigate(R.id.action_nav_intervention_create_to_nav_intervention_details, bundle);
    }

    @Override
    public void onChanged(Intervention intervention) {
        if (mIntervention == null)mIntervention = intervention;
        editTextTitle.setText(intervention.getTitle());
        editTextDescription.setText(intervention.getDescription());
        editTextDate.setText(intervention.getDate());
        for (int i = 0; i <clientSpinner.getAdapter().getCount() ; i++) {
            if (clientSpinner.getItemIdAtPosition(i) == mClients.get(i).getId())clientSpinner.setSelection(i);
        }
    }
}