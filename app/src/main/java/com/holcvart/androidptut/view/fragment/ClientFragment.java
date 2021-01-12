package com.holcvart.androidptut.view.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.repository.ClientRepository;
import com.holcvart.androidptut.view.model.ClientViewModel;
import com.holcvart.androidptut.view.model.CustomViewModelFactory;
import com.holcvart.androidptut.view.recycler_view.ClientListAdapter;

public class ClientFragment extends Fragment {

    private ClientViewModel clientViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private EditText editTextName;
    private EditText editTextFirstname;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CustomViewModelFactory viewModelFactory= new CustomViewModelFactory(new ClientRepository(((MainActivity)requireActivity()).getDatabase()));
        clientViewModel =
                new ViewModelProvider(this, viewModelFactory).get(ClientViewModel.class);
        floatingActionButton = ((MainActivity)requireActivity()).getFloatingActionButton();
        customizeFloatingActionButton();
        View root = inflater.inflate(R.layout.fragment_client, container, false);
        recyclerView = root.findViewById(R.id.clientRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(new ClientListAdapter(clientViewModel.findAll(), this));
        return root;
    }

    private void customizeFloatingActionButton(){
        floatingActionButton.setImageResource(android.R.drawable.ic_input_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("create new Client", "New Client");
                Navigation.findNavController(getView()).navigate(R.id.action_nav_client_to_client_create);
            }
        });
    }

    public void navToDetailedView(View view, int position) {
        long id= Long.parseLong(String.valueOf(recyclerView.getAdapter().getItemId(position)));
        Bundle bundle = new Bundle();
        bundle.putLong("clientId", id);
        Navigation.findNavController(view).navigate(R.id.action_nav_client_to_nav_client_details, bundle);
    }
}