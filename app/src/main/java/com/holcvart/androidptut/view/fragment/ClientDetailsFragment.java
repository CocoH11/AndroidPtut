package com.holcvart.androidptut.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.repository.ClientRepository;
import com.holcvart.androidptut.view.model.ClientDetailViewModel;
import com.holcvart.androidptut.view.model.ClientDetailsViewModelFactory;

public class ClientDetailsFragment extends Fragment {
    private ClientDetailViewModel clientDetailViewModel;
    private TextView textViewFirstName;
    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewPhone;
    private TextView textViewAddress;
    private Button buttonPhoneCall;
    private Button buttonEmailSend;
    private Button buttonGetEstimates;

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
        buttonPhoneCall=(Button)view.findViewById(R.id.buttonPhoneCall);
        buttonEmailSend=(Button)view.findViewById(R.id.buttonEmailSend);
        buttonGetEstimates=(Button)view.findViewById(R.id.buttonGetEstimates);

        Client client = clientDetailViewModel.findOneById(getArguments().getLong("clientId"));
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
}