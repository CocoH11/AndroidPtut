package com.holcvart.androidptut.view.model;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.holcvart.androidptut.model.database.PhoneRepairManagementDBHelper;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientDetailViewModel extends AndroidViewModel {
    private ClientRepository clientRepository;
    private MutableLiveData<Client> client;

    public ClientDetailViewModel(Application application){
        super(application);
        SQLiteDatabase database = new PhoneRepairManagementDBHelper(application.getBaseContext()).getWritableDatabase();
        clientRepository = new ClientRepository(database);
    }

    public MutableLiveData<Client> getClient(long id){
        if (client == null){
            client = new MutableLiveData<>();
            loadClient(id);
        }
        return client;
    }

    public void loadClient(long id){
        Client newClient = new Client();
        clientRepository.findOneById(id, newClient, null);
        client.setValue(newClient);
    }
}
