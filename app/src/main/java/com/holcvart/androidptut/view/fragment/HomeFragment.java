package com.holcvart.androidptut.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.view.model.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FloatingActionButton floatingActionButton;
    private static String FILE_NAME_SHARED_PREFERENCES;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        floatingActionButton = ((MainActivity)requireActivity()).getFloatingActionButton();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        FILE_NAME_SHARED_PREFERENCES = getString(R.string.sharedPreferencesFileName);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(FILE_NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE);

        if (!sharedPreferences.getBoolean("isFirstTime",false)){
            new AlertDialog.Builder(getContext())
                    .setTitle("Première ouverture de l'application")
                    .setMessage("Voulez-vous remplir les données d'entreprise maintenant dans les paramètres")

                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Bundle bundle = new Bundle();
                            Navigation.findNavController(root).navigate(R.id.action_nav_home_to_nav_settings,bundle);
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("Non", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
        return root;
    }

    private void customizeFloatingActionButton(){
        floatingActionButton.setImageResource(android.R.drawable.ic_input_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Replace with your own action", (int) 2).show();
            }
        });
    }
}