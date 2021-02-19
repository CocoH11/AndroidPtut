package com.holcvart.androidptut.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.holcvart.androidptut.R;


public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName;
    private EditText editTextAdresse;
    private EditText editTextCodePostal;
    private EditText editTextVille;
    private EditText editTextTelephone;
    private EditText editTextPortable;
    private EditText editTextMail;
    private EditText editTextSiret;

    private Button buttonSaveSettings;

    public static String FILE_NAME_SHARED_PREFERENCES;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_fragment);

        buttonSaveSettings = this.findViewById(R.id.buttonSettingEntrepriseSave);
        buttonSaveSettings.setOnClickListener(this);
    }

    private void saveDataApplication(View view) {
        editTextName = view.findViewById(R.id.editTextEntrepriseSettingName);
        String name = editTextName.getText().toString();
        editTextAdresse = view.findViewById(R.id.editTextSettingEntrepriseAdresse);
        String adresse = editTextAdresse.getText().toString();
        editTextCodePostal = view.findViewById(R.id.editTextSettingEntrepriseCodePostal);
        String codePostal = editTextCodePostal.getText().toString();
        editTextVille = view.findViewById(R.id.editTextSettingEntrepriseVille);
        String ville = editTextVille.getText().toString();
        editTextTelephone = view.findViewById(R.id.editTextSettingEntrepriseTelephone);
        String telephone = editTextTelephone.getText().toString();
        editTextPortable = view.findViewById(R.id.editTextSettingEntreprisePortable);
        String portable = editTextPortable.getText().toString();
        editTextMail = view.findViewById(R.id.editTextSettingEntrepriseMail);
        String mail = editTextMail.getText().toString();
        editTextSiret = view.findViewById(R.id.editTextSettingEntrepriseSiret);
        String siret = editTextSiret.getText().toString();

        if (!name.equals("") && !adresse.equals("") && !codePostal.equals("") && !ville.equals("") && (!telephone.equals("") || !portable.equals("") ) && !mail.equals("") && !siret.equals("")){
            FILE_NAME_SHARED_PREFERENCES = getString(R.string.sharedPreferencesFileName);

            SharedPreferences profileSharedPreferences = getSharedPreferences(FILE_NAME_SHARED_PREFERENCES,MODE_PRIVATE);
            SharedPreferences.Editor editor = profileSharedPreferences.edit();

            editor.putString("name", name);
            editor.putString("adresse", adresse);
            editor.putString("code_postal", codePostal);
            editor.putString("ville", ville);
            editor.putString("mail", mail);
            editor.putString("siret", siret);
            if (!telephone.equals("") && !portable.equals("")){
                editor.putString("telephone", telephone);
                editor.putString("portable", portable);
            }else if(!telephone.equals("")){
                editor.putString("telephone", telephone);
            }else {
                editor.putString("telephone", portable);
            }
            editor.apply();
        }else {
            Toast.makeText(getApplicationContext(), "Il manque des informations, veuillez remplir tous les champs de text", Toast.LENGTH_LONG).show();
        }


    }



    @Override
    public void onClick(View v) {
        saveDataApplication(v);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
