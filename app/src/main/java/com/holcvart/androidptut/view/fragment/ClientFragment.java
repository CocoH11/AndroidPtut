package com.holcvart.androidptut.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.view.model.ClientViewModel;
import com.holcvart.androidptut.view.recycler_view.ClientListAdapter;

import java.util.List;

public class ClientFragment extends Fragment implements Observer<List<Client>>{

    private ClientViewModel clientViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private List<Client> mClients;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        floatingActionButton = ((MainActivity)requireActivity()).getFloatingActionButton();
        customizeFloatingActionButton();
        View root = inflater.inflate(R.layout.fragment_client, container, false);
        recyclerView = root.findViewById(R.id.clientRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        clientViewModel.getClients().observe(getViewLifecycleOwner(), this);
        return root;
    }

    private void customizeFloatingActionButton(){
        floatingActionButton.setImageResource(android.R.drawable.ic_input_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_nav_client_to_client_create);
            }
        });
        floatingActionButton.show();
    }

    public void navToDetailedView(View view, int position) {
        long id= Long.parseLong(String.valueOf(recyclerView.getAdapter().getItemId(position)));
        Bundle bundle = new Bundle();
        bundle.putLong("clientId", id);
        Navigation.findNavController(view).navigate(R.id.action_nav_client_to_nav_client_details, bundle);
    }

    private ClientFragment getClientFragment(){
        return this;
    }

    @Override
    public void onChanged(List<Client> clients) {
        if(mClients == null)mClients = clients;
        recyclerView.setAdapter(new ClientListAdapter(mClients, getClientFragment()));
    }
}