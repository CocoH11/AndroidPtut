package com.holcvart.androidptut.view.model;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.database.PhoneRepairManagementDBHelper;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.repository.ClientRepository;

public class ClientCreateViewModel extends AndroidViewModel {
    private ClientRepository clientRepository;
    public ClientCreateViewModel(Application application) {
        super(application);
        SQLiteDatabase database = new PhoneRepairManagementDBHelper(application.getApplicationContext()).getWritableDatabase();
        clientRepository = new ClientRepository(database);
    }

    public void insert(Client client){
        clientRepository.insert(client);
    }
}