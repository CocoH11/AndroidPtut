package com.holcvart.androidptut.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.repository.ClientRepository;
import com.holcvart.androidptut.view.model.ClientDetailViewModel;
import com.holcvart.androidptut.view.model.ClientDetailsViewModelFactory;
import com.holcvart.androidptut.view.model.ClientViewModelFactory;
import com.holcvart.androidptut.view.model.EstimateViewModel;

public class ClientDetailsFragment extends Fragment {
    private ClientDetailViewModel clientDetailViewModel;
    private TextView textViewFirstName;
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewPhone;
    private TextView textViewAddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ClientDetailsViewModelFactory viewModelFactory= new ClientDetailsViewModelFactory(new ClientRepository(((MainActivity)requireActivity()).getDatabase()));
        clientDetailViewModel = new ViewModelProvider(this, viewModelFactory).get(ClientDetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_client_details, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        textViewFirstName= (TextView)view.findViewById(R.id.textViewClientFirstName);
        textViewName=(TextView)view.findViewById(R.id.textViewClientName);
        textViewEmail=(TextView)view.findViewById(R.id.textViewClientEmail);
        textViewPhone=(TextView)view.findViewById(R.id.textViewClientPhone);
        textViewAddress=(TextView)view.findViewById(R.id.textViewClientAddress);
        Client client = clientDetailViewModel.findOneById(getArguments().getLong("clientId"));
        textViewFirstName.setText(client.getFirstName());
        textViewName.setText(client.getName());
        textViewEmail.setText(client.getEmail());
        textViewPhone.setText(client.getPhone());
        textViewAddress.setText(client.getAddress());
    }
}