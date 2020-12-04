package com.holcvart.androidptut.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
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
        recyclerView.setAdapter(new ClientListAdapter(clientViewModel.findAll(), this));
        return root;
    }

    public void navToDetailedView(View view, int position) {
        long id= Long.parseLong(String.valueOf(recyclerView.getAdapter().getItemId(position)));
        Bundle bundle = new Bundle();
        bundle.putLong("clientId", id);
        Navigation.findNavController(view).navigate(R.id.action_nav_client_to_nav_client_details, bundle);
    }
}