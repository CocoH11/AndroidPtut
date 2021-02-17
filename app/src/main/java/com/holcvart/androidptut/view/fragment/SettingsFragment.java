package com.holcvart.androidptut.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;


public class SettingsFragment extends Fragment {

    private FloatingActionButton floatingActionButton;
    private ActionBar actionBar;

    private EditText editTextName;
    private EditText editTextAdresse;
    private EditText editTextCodePostal;
    private EditText editTextVille;
    private EditText editTextTelephone;
    private EditText editTextPortable;
    private EditText editTextMail;
    private EditText editTextSiret;

    private Button buttonSaveSettings;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        floatingActionButton = ((MainActivity)requireActivity()).getFloatingActionButton();
        floatingActionButton.hide();
        actionBar = ((MainActivity)requireActivity()).getSupportActionBar();
        customizeActionBar();
        View root = inflater.inflate(R.layout.settings_fragment, container, false);
        onConfigurationChanged(getActivity().getResources().getConfiguration());
//        buttonSaveSettings.findViewById(R.id.buttonSettingEntrepriseSave);
//        buttonSaveSettings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveDataApplication(v);
//            }
//        });
        return root;
    }

    private void saveDataApplication(View view) {
        editTextName = view.findViewById(R.id.editTextEntrepriseSettingName);
        editTextAdresse = view.findViewById(R.id.editTextSettingEntrepriseAdresse);
        editTextCodePostal = view.findViewById(R.id.editTextSettingEntrepriseCodePostal);
        editTextVille = view.findViewById(R.id.editTextSettingEntrepriseVille);
        editTextTelephone = view.findViewById(R.id.editTextSettingEntrepriseTelephone);
        editTextPortable = view.findViewById(R.id.editTextSettingEntreprisePortable);
        editTextMail = view.findViewById(R.id.editTextSettingEntrepriseMail);
        editTextSiret = view.findViewById(R.id.editTextSettingEntrepriseSiret);

        String text = editTextName.getText().toString();


    }


    private void customizeActionBar(){
        actionBar.setSubtitle(null);
    }


}
