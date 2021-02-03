package com.holcvart.androidptut.view.model;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.holcvart.androidptut.model.database.PhoneRepairManagementDBHelper;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.model.repository.ClientRepository;
import com.holcvart.androidptut.model.repository.InterventionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterventionCreateViewModel extends AndroidViewModel {
    private InterventionRepository interventionRepository;
    private ClientRepository clientRepository;
    private MutableLiveData<Intervention> intervention;
    private MutableLiveData<List<Client>> clients;

    public InterventionCreateViewModel(Application application) {
        super(application);
        SQLiteDatabase database = new PhoneRepairManagementDBHelper(application.getApplicationContext()).getWritableDatabase();
        interventionRepository = new InterventionRepository(database);
        clientRepository = new ClientRepository(database);
    }

    public void insertOrUpdate(){
        System.out.println("intervention id in create fragment: " + intervention.getValue().getId());
        if (intervention.getValue().getId()==-1) interventionRepository.insert(intervention.getValue());
        else interventionRepository.update(intervention.getValue());
    }

    public MutableLiveData<Intervention> getIntervention(){
        return getIntervention(-1);
    }

    public MutableLiveData<Intervention> getIntervention(long id){
        if (intervention == null){
            intervention = new MutableLiveData<>();
            loadIntervention(id);
        }
        return intervention;
    }

    public void loadIntervention(long id){
        Intervention newIntervention = new Intervention();
        Map<String, String[]> args = new HashMap<>();
        if (id != -1) interventionRepository.findOneById(id, newIntervention, args);
        else {
            newIntervention.setId(id);
            newIntervention.setClient(new Client());
        }
        intervention.setValue(newIntervention);
    }

    public MutableLiveData<List<Client>> getClients(){
        if (clients == null){
            clients = new MutableLiveData<>();
            loadClients();
        }
        return clients;
    }

    public void loadClients(){
        List<Entity>  entities = new ArrayList<>();
        clientRepository.findAll(entities);
        List<Client> newClients = (List<Client>)(List<?>)entities;
        clients.setValue(newClients);
    }
}