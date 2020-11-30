package com.holcvart.androidptut.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.repository.ClientRepository;
import com.holcvart.androidptut.view.model.ClientViewModel;
import com.holcvart.androidptut.view.model.ClientViewModelFactory;
import com.holcvart.androidptut.view.recycler_view.ClientListAdapter;

public class ClientFragment extends Fragment {

    private ClientViewModel clientViewModel;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ClientViewModelFactory viewModelFactory= new ClientViewModelFactory(new ClientRepository(((MainActivity)requireActivity()).getDatabase()));
        clientViewModel =
                new ViewModelProvider(this, viewModelFactory).get(ClientViewModel.class);
        View root = inflater.inflate(R.layout.fragment_client, container, false);
        recyclerView = root.findViewById(R.id.clientRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(new ClientListAdapter(clientViewModel.findAll()));
        return root;
    }
}