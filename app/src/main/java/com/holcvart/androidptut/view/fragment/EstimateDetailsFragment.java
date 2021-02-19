package com.holcvart.androidptut.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import com.holcvart.androidptut.view.model.EstimateDetailsViewModel;

public class EstimateDetailsFragment extends Fragment implements View.OnClickListener, Observer<Intervention> {
    private EstimateDetailsViewModel mViewModel;
    private TextView textViewTitle;
    private TextView textViewDateIntervention;
    private TextView textViewDescription;
    private TextView textViewClientName;
    private TextView textViewClientFirstName;
    private TextView textViewClientPhone;
    private TableLayout tableLayout;
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
        textViewDescription = (TextView) view.findViewById(R.id.textViewEstimateDetailsDescription);
        textViewTitle = (TextView) view.findViewById(R.id.textViewEstimateDetailsTitle);
        textViewDateIntervention = (TextView) view.findViewById(R.id.textViewEstimateDetailsDate);
        textViewClientFirstName = (TextView) view.findViewById(R.id.textViewEstimateDetailsClientFirstName);
        textViewClientName = (TextView) view.findViewById(R.id.textViewEstimateDetailsClientName);
        textViewClientPhone = (TextView) view.findViewById(R.id.textViewEstimateDetailsClientPhone);
        tableLayout =  (TableLayout) view.findViewById(R.id.partTable);
        customizeFloatingActionButton();
        mViewModel.getIntervention(getArguments().getLong("interventionId")).observe(getViewLifecycleOwner(), this);
    }

    private void customizeFloatingActionButton(){
        floatingActionButton.setImageResource(android.R.drawable.ic_menu_edit);
        floatingActionButton.setOnClickListener(this);
        floatingActionButton.show();
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
        textViewTitle.setText(intervention.getTitle());
        textViewDateIntervention.setText(intervention.getDate());
        textViewClientFirstName.setText(intervention.getClient().getFirstName());
        textViewClientName.setText(intervention.getClient().getName());
        textViewClientPhone.setText(intervention.getClient().getPhone());
        textViewDescription.setText(intervention.getDescription());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (PartsNeeded parts:intervention.getPartsNeededs()) {
            TableRow row = (TableRow)inflater.inflate(R.layout.part_table_row, null);
            ((TextView)row.findViewById(R.id.textViewNaming1)).setText(parts.getPart().getNaming());
            ((TextView)row.findViewById(R.id.textViewQuantity1)).setText(String.valueOf(parts.getQuantity()));
            ((TextView)row.findViewById(R.id.textViewLinePriceHT1)).setText(String.valueOf(parts.getPart().getBillPrice() * parts.getQuantity()));
            ((TextView)row.findViewById(R.id.textViewLinePriceTTC1)).setText(String.valueOf(parts.getPart().getBillPrice() * parts.getQuantity()));
            ((TextView)row.findViewById(R.id.textViewUnitPrice1)).setText(String.valueOf(parts.getPart().getBillPrice()));
            tableLayout.addView(row);
        }
    }
}