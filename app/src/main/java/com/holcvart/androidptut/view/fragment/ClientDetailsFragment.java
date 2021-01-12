package com.holcvart.androidptut.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.repository.ClientRepository;
import com.holcvart.androidptut.view.model.ClientDetailViewModel;
import com.holcvart.androidptut.view.model.CustomViewModelFactory;

public class ClientDetailsFragment extends Fragment {
    private ClientDetailViewModel clientDetailViewModel;
    private Client client;
    private TextView textViewFirstName;
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewPhone;
    private TextView textViewAddress;
    private Button buttonPhoneCall;
    private Button buttonEmailSend;
    private Button buttonGetEstimates;
    private FloatingActionButton floatingActionButton;
    private ActionBar actionBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CustomViewModelFactory viewModelFactory= new CustomViewModelFactory(new ClientRepository(((MainActivity)requireActivity()).getDatabase()));
        clientDetailViewModel = new ViewModelProvider(this, viewModelFactory).get(ClientDetailViewModel.class);
        client = clientDetailViewModel.findOneById(getArguments().getLong("clientId"));
        return inflater.inflate(R.layout.fragment_client_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        floatingActionButton = ((MainActivity)requireActivity()).getFloatingActionButton();
        actionBar = ((MainActivity)requireActivity()).getSupportActionBar();

        textViewFirstName= (TextView)view.findViewById(R.id.textViewClientFirstName);
        textViewName=(TextView)view.findViewById(R.id.textViewClientName);
        textViewEmail=(TextView)view.findViewById(R.id.textViewClientEmail);
        textViewPhone=(TextView)view.findViewById(R.id.textViewClientPhone);
        textViewAddress=(TextView)view.findViewById(R.id.textViewClientAddress);
        buttonPhoneCall=(Button)view.findViewById(R.id.buttonPhoneCall);
        buttonEmailSend=(Button)view.findViewById(R.id.buttonEmailSend);
        buttonGetEstimates=(Button)view.findViewById(R.id.buttonGetEstimates);

        customizeFloatingActionButton();
        customizeActionBar();

        textViewFirstName.setText(client.getFirstName());
        textViewName.setText(client.getName());
        textViewEmail.setText(client.getEmail());
        textViewPhone.setText(client.getPhone());
        textViewAddress.setText(client.getAddress());

        buttonPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber=textViewPhone.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        buttonEmailSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] addresses=new String[]{textViewEmail.getText().toString()};
                String subject= "subject";
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        buttonGetEstimates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void customizeFloatingActionButton(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("create new Client", "New Client");
                //TODO: Create new Client
            }
        });
    }

    private void customizeActionBar(){
        actionBar.setTitle(client.getFirstName()+" "+client.getName());
    }
}