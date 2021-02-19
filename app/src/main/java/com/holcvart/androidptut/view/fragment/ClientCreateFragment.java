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
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.view.model.ClientCreateViewModel;

public class ClientCreateFragment extends Fragment implements Observer<Client> {
    private EditText editTextName;
    private EditText editTextFirstname;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextAddress;

    private ClientCreateViewModel mViewModel;
    private FloatingActionButton floatingActionButton;
    private ActionBar actionBar;
    private Client mClient;

    public static ClientCreateFragment newInstance() {
        return new ClientCreateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        actionBar = ((MainActivity)requireActivity()).getSupportActionBar();
        floatingActionButton = ((MainActivity)requireActivity()).getFloatingActionButton();
        mViewModel = new ViewModelProvider(this).get(ClientCreateViewModel.class);
        return inflater.inflate(R.layout.client_create_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        customizeActionBar();
        customizeFloatingActionButton();
        editTextName = (EditText)view.findViewById(R.id.editTextName);
        editTextFirstname = (EditText)view.findViewById(R.id.editTextFirstname);
        editTextPhone = (EditText)view.findViewById(R.id.editTextPhone);
        editTextEmail = (EditText)view.findViewById(R.id.editTextEmail);
        editTextAddress = (EditText)view.findViewById(R.id.editTextAddress);
        if (getArguments() != null)mViewModel.getClient(getArguments().getLong("clientId")).observe(getViewLifecycleOwner(), this);
        else mViewModel.getClient().observe(getViewLifecycleOwner(), this);

    }

    private void customizeActionBar(){
        actionBar.setTitle("Nouveau client");
    }
    private void customizeFloatingActionButton(){
        floatingActionButton.setImageResource(android.R.drawable.ic_media_next);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClient.setName(editTextName.getText().toString());
                mClient.setFirstName(editTextFirstname.getText().toString());
                mClient.setPhone(editTextPhone.getText().toString());
                mClient.setEmail(editTextEmail.getText().toString());
                mClient.setAddress(editTextAddress.getText().toString());
                mViewModel.insertOrUpdate();

                navToDetailedView(getView());
            }
        });
        floatingActionButton.show();
    }

    public void navToDetailedView(View view) {
        long id= mClient.getId();
        Bundle bundle = new Bundle();
        bundle.putLong("clientId", id);
        Navigation.findNavController(view).navigate(R.id.action_nav_client_create_to_nav_client_details, bundle);
    }

    @Override
    public void onChanged(Client client) {
        if (mClient == null)mClient = client;
        editTextName.setText(client.getName());
        editTextFirstname.setText(client.getFirstName());
        editTextPhone.setText(client.getPhone());
        editTextEmail.setText(client.getEmail());
        editTextAddress.setText(client.getAddress());
    }
}