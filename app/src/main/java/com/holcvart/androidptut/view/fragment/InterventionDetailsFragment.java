package com.holcvart.androidptut.view.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.model.entity.PartsNeeded;
import com.holcvart.androidptut.view.model.InterventionDetailsViewModel;

public class InterventionDetailsFragment extends Fragment implements View.OnClickListener, Observer<Intervention> {
    private InterventionDetailsViewModel interventionDetailsViewModel;
    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewDate;
    private TextView textViewClientFirstName;
    private TextView textViewClientName;
    private TextView textViewClientPhone;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private TableLayout tableLayout;
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
                NavHostFragment.findNavController(this).navigate(R.id.action_nav_intervention_details_to_nav_intervention);
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
        textViewTitle = (TextView) view.findViewById(R.id.textViewInterventionDetailsTitle);
        textViewDate = (TextView) view.findViewById(R.id.textViewInterventionDetailsDate);
        textViewDescription=(TextView)view.findViewById(R.id.textViewInterventionDetailsDescription);
        textViewClientFirstName = (TextView) view.findViewById(R.id.textViewInterventionDetailsClientFirstName);
        textViewClientName = (TextView) view.findViewById(R.id.textViewInterventionDetailsClientName);
        textViewClientPhone = (TextView) view.findViewById(R.id.textViewInterventionDetailsClientPhone);
        tableLayout = (TableLayout)view.findViewById(R.id.interventionPartTable);
        customizeFloatingActionButton();
        interventionDetailsViewModel.getIntervention(getArguments().getLong("interventionId")).observe(getViewLifecycleOwner(), this);
    }

    private void customizeFloatingActionButton(){
        floatingActionButton.setImageResource(android.R.drawable.ic_menu_edit);
        floatingActionButton.setOnClickListener(this);
        floatingActionButton.hide();
    }

    private void customizeActionBar(){
        actionBar.setTitle(mIntervention.getTitle());
        actionBar.setSubtitle(mIntervention.getDate());
    }

    private void customizeBackNavigation(){
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new CustomBackPressed(true, this, R.id.action_nav_intervention_details_to_nav_intervention));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == floatingActionButton.getId()){
            navToUpdateView(getView());
        }
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
        textViewTitle.setText(mIntervention.getTitle());
        textViewDate.setText(mIntervention.getDate());
        textViewDescription.setText(mIntervention.getDescription());
        textViewClientFirstName.setText(mIntervention.getClient().getFirstName());
        textViewClientName.setText(mIntervention.getClient().getName());
        textViewClientPhone.setText(mIntervention.getClient().getPhone());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (PartsNeeded parts : mIntervention.getPartsNeededs()) {
            TableRow row = (TableRow) inflater.inflate(R.layout.intervention_part_table_row, null);
            ((TextView)row.findViewById(R.id.textViewNaming2)).setText(parts.getPart().getNaming());
            ((TextView)row.findViewById(R.id.textViewQuantity2)).setText(String.valueOf(parts.getQuantity()));
            tableLayout.addView(row);
        }
    }
}