package com.holcvart.androidptut.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.holcvart.androidptut.DatabaseTest;
import com.holcvart.androidptut.MainActivity;
import com.holcvart.androidptut.R;
import com.holcvart.androidptut.model.database.PhoneRepairManagementDBHelper;


public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_fragment);

        buttonSaveSettings = this.findViewById(R.id.buttonSettingEntrepriseSave);
        buttonSaveSettings.setOnClickListener(this);
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



    @Override
    public void onClick(View v) {
        saveDataApplication(v);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
