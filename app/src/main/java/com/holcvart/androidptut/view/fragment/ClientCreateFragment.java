package com.holcvart.androidptut.view.fragment;

import androidx.appcompat.app.ActionBar;
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
import com.holcvart.androidptut.model.repository.ClientRepository;
import com.holcvart.androidptut.view.model.ClientCreateViewModel;

public class ClientCreateFragment extends Fragment {
    private EditText editTextName;
    private EditText editTextFirstname;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextAddress;

    private ClientCreateViewModel mViewModel;
    private FloatingActionButton floatingActionButton;
    private ActionBar actionBar;

    public static ClientCreateFragment newInstance() {
        return new ClientCreateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        actionBar = ((MainActivity)requireActivity()).getSupportActionBar();
        floatingActionButton = ((MainActivity)requireActivity()).getFloatingActionButton();
        return inflater.inflate(R.layout.client_create_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ClientCreateViewModel.class);
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
    }

    private void customizeActionBar(){
        actionBar.setTitle("Nouveau client");
    }
    private void customizeFloatingActionButton(){
        floatingActionButton.setImageResource(android.R.drawable.ic_media_next);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client client = new Client();
                client.setName(editTextName.getText().toString());
                client.setFirstName(editTextFirstname.getText().toString());
                client.setPhone(editTextPhone.getText().toString());
                client.setEmail(editTextEmail.getText().toString());
                client.setAddress(editTextAddress.getText().toString());
                mViewModel.insert(client);

                navToDetailedView(getView(), client);
            }
        });
    }

    public void navToDetailedView(View view, Client client) {
        long id= client.getId();
        Bundle bundle = new Bundle();
        bundle.putLong("clientId", id);
        Navigation.findNavController(view).navigate(R.id.action_nav_client_create_to_nav_client_details, bundle);
    }

}