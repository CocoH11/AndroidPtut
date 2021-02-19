package com.holcvart.androidptut.view.fragment;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.model.entity.Part;
import com.holcvart.androidptut.model.entity.PartsNeeded;
import com.holcvart.androidptut.view.model.InterventionCreateViewModel;

import java.util.List;
import java.util.zip.Inflater;

public class EstimateCreateFragment extends Fragment implements Observer<Intervention>, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextDate;
    private Spinner clientSpinner;
    private Button addPartsButton;
    private TableLayout partsTable;
    private InterventionCreateViewModel mViewModel;
    private FloatingActionButton floatingActionButton;
    private ActionBar actionBar;
    private Intervention mIntervention;
    private List<Client> mClients;
    private List<Part> mParts;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        actionBar = ((MainActivity)requireActivity()).getSupportActionBar();
        floatingActionButton = ((MainActivity)requireActivity()).getFloatingActionButton();
        mViewModel = new ViewModelProvider(this).get(InterventionCreateViewModel.class);
        return inflater.inflate(R.layout.estimate_create_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        customizeActionBar();
        customizeFloatingActionButton();
        editTextTitle = (EditText)view.findViewById(R.id.editTextTitle);
        editTextDescription = (EditText)view.findViewById(R.id.editTextDescription);
        editTextDate = (EditText)view.findViewById(R.id.editTextDate);
        clientSpinner = (Spinner)view.findViewById(R.id.spinnerClient);
        addPartsButton = (Button)view.findViewById(R.id.addPartsButton);
        addPartsButton.setOnClickListener(this);
        partsTable = (TableLayout) view.findViewById(R.id.estimateCreatePartsTable);
        mViewModel.getClients().observe(getViewLifecycleOwner(), new Observer<List<Client>>() {
            @Override
            public void onChanged(List<Client> clients) {
                if (mClients == null)mClients = clients;
                ArrayAdapter adapter = new SpinnerClientAdapter(getContext(), R.layout.adapter_item_client, clients);
                clientSpinner.setAdapter(adapter);
            }
        });
        mViewModel.getParts().observe(getViewLifecycleOwner(), new Observer<List<Part>>() {
            @Override
            public void onChanged(List<Part> parts) {
                if (mParts == null) mParts = parts;
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
        floatingActionButton.setOnClickListener(this);
        floatingActionButton.show();
    }

    public void navToDetailedView(View view) {
        long id= mIntervention.getId();
        Bundle bundle = new Bundle();
        bundle.putLong("interventionId", id);
        Navigation.findNavController(view).navigate(R.id.action_nav_estimate_create_to_nav_estimate_details, bundle);
    }

    @Override
    public void onChanged(Intervention intervention) {
        if (mIntervention == null)mIntervention = intervention;
        editTextTitle.setText(intervention.getTitle());
        editTextDescription.setText(intervention.getDescription());
        editTextDate.setText(intervention.getDate());
        for (int i = 0; i <clientSpinner.getAdapter().getCount() ; i++) {
            if (clientSpinner.getItemIdAtPosition(i) == mIntervention.getClient().getId())clientSpinner.setSelection(i);
        }
        for (int i = 0; i < mIntervention.getPartsNeededs().size(); i++) {
            addPartTabLine(mIntervention.getPartsNeededs().get(i).getPart().getId(), mIntervention.getPartsNeededs().get(i).getQuantity());
        }
    }

    public void addPartTabLine(long id, int quantity){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableRow row = (TableRow) inflater.inflate(R.layout.estimate_create_parts_table_row, null);
        if (id == -1){
            PartsNeeded parts = new PartsNeeded();
            parts.setQuantity(1);
            parts.setPart(mParts.get(0));
            parts.setIntervention(mIntervention);
            mIntervention.getPartsNeededs().add(parts);
        }
        Spinner spinner = (Spinner)row.findViewById(R.id.partSpinner);
        spinner.setAdapter(new SpinnerPartsAdapter(getContext(), R.layout.adapter_item_part, mParts));
        spinner.setOnItemSelectedListener(this);
        Log.d("part id", String.valueOf(id));
        if (id != -1){
            for (int i = 0; i <spinner.getAdapter().getCount() ; i++) {
                if (spinner.getItemIdAtPosition(i) == id)spinner.setSelection(i);
                Log.d("item id", String.valueOf(spinner.getItemIdAtPosition(i)));
            }
        }
        if (quantity != -1)((TextView)row.findViewById(R.id.textviewQuantity)).setText(String.valueOf(quantity));
        else ((TextView)row.findViewById(R.id.textviewQuantity)).setText(String.valueOf(1));
        ((Button)row.findViewById(R.id.buttonQuantityLess)).setOnClickListener(this);
        ((Button)row.findViewById(R.id.buttonQuantityPlus)).setOnClickListener(this);
        partsTable.addView(row);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == floatingActionButton.getId()){
            mIntervention.setTitle(editTextTitle.getText().toString());
            mIntervention.setDescription(editTextDescription.getText().toString());
            mIntervention.setDate(editTextDate.getText().toString());
            Client client = new Client();
            client.setId(clientSpinner.getSelectedItemId());
            mIntervention.setClient(client);
            mViewModel.insertOrUpdate();
            navToDetailedView(getView());
        }
        else if (v.getId() == addPartsButton.getId()) {
            Log.d("View clicked", "AddPartButton");
            addPartTabLine(-1, -1);
        }
        else if (v.getId() == R.id.buttonQuantityLess){
            View container = (View) v.getParent();
            TextView textView = container.findViewById(R.id.textviewQuantity);
            int currentQuantity = Integer.valueOf(textView.getText().toString());
            currentQuantity --;
            if (currentQuantity == 0){
                mIntervention.getPartsNeededs().remove(((TableLayout)container.getParent()).indexOfChild(container)-1);
                ((TableLayout)container.getParent()).removeView(container);
            }else {
                textView.setText(String.valueOf(currentQuantity));
                mIntervention.getPartsNeededs().get(((TableLayout)container.getParent()).indexOfChild(container)-1).setQuantity(currentQuantity);
            }
        }
        else if(v.getId() == R.id.buttonQuantityPlus){
            View container = (View) v.getParent();
            TextView textView = container.findViewById(R.id.textviewQuantity);
            int currentQuantity = Integer.valueOf(textView.getText().toString());
            currentQuantity ++;
            textView.setText(String.valueOf(currentQuantity));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TableRow container = (TableRow)parent.getParent();
        mIntervention.getPartsNeededs().get(((TableLayout)container.getParent()).indexOfChild(container)-1).setPart((Part)parent.getSelectedItem());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}