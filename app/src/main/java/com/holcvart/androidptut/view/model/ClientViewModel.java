package com.holcvart.androidptut.view.model;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.holcvart.androidptut.model.database.PhoneRepairManagementDBHelper;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientViewModel extends AndroidViewModel {
    private ClientRepository clientRepository;
    private MutableLiveData<List<Client>> clients;

    public ClientViewModel(Application application) {
        super(application);
        SQLiteDatabase database = new PhoneRepairManagementDBHelper(application.getBaseContext()).getWritableDatabase();
        clientRepository = new ClientRepository(database);
    }

    public MutableLiveData<List<Client>> getClients() {
        if (clients == null) {
            clients = new MutableLiveData<>();
            loadClients();
        }
        return clients;
    }

    public void loadClients(){
        List<Entity> entities= new ArrayList<>();
        clientRepository.findAll(entities);
        List<Client> newClients = (List<Client>)(List<?>)entities;
        clients.setValue(newClients);
    }
}