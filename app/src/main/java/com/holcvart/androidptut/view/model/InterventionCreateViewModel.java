package com.holcvart.androidptut.view.model;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.database.PhoneRepairManagementDBHelper;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.model.entity.Part;
import com.holcvart.androidptut.model.repository.ClientRepository;
import com.holcvart.androidptut.model.repository.InterventionRepository;
import com.holcvart.androidptut.model.repository.PartRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_DATE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_DESCRIPTION;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_ID_CLIENT;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_VALID;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_TITLE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.SQL_ORDER_BY_DATE_DESC;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.SQL_WHERE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention._ID;

public class InterventionCreateViewModel extends AndroidViewModel {
    private InterventionRepository interventionRepository;
    private ClientRepository clientRepository;
    private PartRepository partRepository;
    private MutableLiveData<Intervention> intervention;
    private MutableLiveData<List<Client>> clients;
    private MutableLiveData<List<Part>> parts;

    public InterventionCreateViewModel(Application application) {
        super(application);
        SQLiteDatabase database = new PhoneRepairManagementDBHelper(application.getApplicationContext()).getWritableDatabase();
        interventionRepository = new InterventionRepository(database);
        clientRepository = new ClientRepository(database);
        partRepository = new PartRepository(database);
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
        if (id != -1) {
            String[] columns = new String[]{

                    PhoneRepairManagementContract.Intervention._ID,
                    PhoneRepairManagementContract.Intervention.COLUMN_NAME_TITLE,
                    PhoneRepairManagementContract.Intervention.COLUMN_NAME_DESCRIPTION,
                    PhoneRepairManagementContract.Intervention.COLUMN_NAME_DATE,
                    PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_BILLED,
                    PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_VALID,
                    PhoneRepairManagementContract.Intervention.COLUMN_NAME_ID_CLIENT,

                    PhoneRepairManagementContract.Client._ID,
                    PhoneRepairManagementContract.Client.COLUMN_NAME_NAME,
                    PhoneRepairManagementContract.Client.COLUMN_NAME_FIRST_NAME,

                    PhoneRepairManagementContract.Part._ID,
                    PhoneRepairManagementContract.Part.COLUMN_NAME_NAMING,

                    PhoneRepairManagementContract.Need._ID,
                    PhoneRepairManagementContract.Need.COLUMN_NAME_QUANTITY

            };
            String[] whereArgs = new String[]{PhoneRepairManagementContract.Intervention._ID};
            String selection = PhoneRepairManagementContract.Intervention.SQL_WHERE(whereArgs);
            String[] selectionArgs = new String[]{String.valueOf(id)};
            String sortOrder = PhoneRepairManagementContract.Intervention.SQL_ORDER_BY_DATE_DESC;
            interventionRepository.find(newIntervention, columns, selection, selectionArgs, sortOrder);
        }
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

    public MutableLiveData<List<Part>> getParts(){
        if (parts == null){
            parts = new MutableLiveData<>();
            loadParts();
        }
        return parts;
    }

    public void loadParts(){
        List<Entity> entities = new ArrayList<>();
        String[] columns = new String[]{PhoneRepairManagementContract.Part._ID, PhoneRepairManagementContract.Part.COLUMN_NAME_NAMING};
        String[] selectionArgs = null;
        String selection = null;
        String order = null;
        partRepository.find2(entities, columns, selection, selectionArgs, order);
        List<Part> newParts = (List<Part>)(List<?>)entities;
        parts.setValue(newParts);
    }
}