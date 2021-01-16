package com.holcvart.androidptut.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.view.model.ClientDetailViewModel;

public class ClientDetailsFragment extends Fragment implements Observer<Client>, View.OnClickListener {
    private ClientDetailViewModel clientDetailViewModel;
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
    private Client mClient;

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
                Navigation.findNavController(getView()).navigate(R.id.action_nav_client_details_to_nav_client);
                break;
        }
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        clientDetailViewModel = new ViewModelProvider(this).get(ClientDetailViewModel.class);
        View root = inflater.inflate(R.layout.fragment_client_details, container, false);
        return root;
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
        clientDetailViewModel.getClient(getArguments().getLong("clientId")).observe(getViewLifecycleOwner(), this);
        buttonPhoneCall.setOnClickListener(this);
        buttonEmailSend.setOnClickListener(this);
        buttonGetEstimates.setOnClickListener(this);
    }

    private void customizeFloatingActionButton(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navToUpdateView(getView());
            }
        });
    }

    private void customizeActionBar(){
        actionBar.setTitle(mClient.getFirstName()+" "+mClient.getName());
    }

    private void customizeBackNavigation(){
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(getClientDetailsFragment()).navigate(R.id.action_nav_client_details_to_nav_client);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onChanged(Client client) {
        if (mClient == null)mClient = client;
        customizeActionBar();
        textViewFirstName.setText(mClient.getFirstName());
        textViewName.setText(mClient.getName());
        textViewEmail.setText(mClient.getEmail());
        textViewPhone.setText(mClient.getPhone());
        textViewAddress.setText(mClient.getAddress());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonPhoneCall:
                phoneCall();
                break;
            case R.id.buttonEmailSend:
                emailSend();
                break;
            case R.id.buttonGetEstimates:
                estimatesGet();
                break;
        }
    }

    private void estimatesGet() {
    }

    public void phoneCall(){
        String phoneNumber=mClient.getPhone();
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    public void emailSend(){
        String[] addresses=new String[]{mClient.getEmail()};
        String subject= "subject";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void navToUpdateView(View view) {
        long id= mClient.getId();
        Bundle bundle = new Bundle();
        bundle.putLong("clientId", id);
        Navigation.findNavController(view).navigate(R.id.action_nav_client_details_to_nav_client_create, bundle);
    }

    public ClientDetailsFragment getClientDetailsFragment(){
        return this;
    }

}