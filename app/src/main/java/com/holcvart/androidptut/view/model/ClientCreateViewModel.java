package com.holcvart.androidptut.view.model;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.database.PhoneRepairManagementDBHelper;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.repository.ClientRepository;

public class ClientCreateViewModel extends AndroidViewModel {
    private ClientRepository clientRepository;
    private MutableLiveData<Client> client;

    public ClientCreateViewModel(Application application) {
        super(application);
        SQLiteDatabase database = new PhoneRepairManagementDBHelper(application.getApplicationContext()).getWritableDatabase();
        clientRepository = new ClientRepository(database);
    }

    public void insertOrUpdate(){
        if (client.getValue().getId()==-1)clientRepository.insert(client.getValue());
        else clientRepository.update(client.getValue());
    }

    public MutableLiveData<Client> getClient(){
        return getClient(-1);
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
        if (id != -1)clientRepository.findOneById(id, newClient);
        else newClient.setId(id);
        client.setValue(newClient);
    }
}