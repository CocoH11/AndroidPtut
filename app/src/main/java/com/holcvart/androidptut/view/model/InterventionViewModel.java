package com.holcvart.androidptut.view.model;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.holcvart.androidptut.model.database.PhoneRepairManagementDBHelper;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.model.repository.ClientRepository;
import com.holcvart.androidptut.model.repository.InterventionRepository;

import java.util.ArrayList;
import java.util.List;

public class InterventionViewModel extends AndroidViewModel {
    private InterventionRepository interventionRepository;
    private MutableLiveData<List<Intervention>> interventions;

    public InterventionViewModel(Application application) {
        super(application);
        SQLiteDatabase database = new PhoneRepairManagementDBHelper(application.getBaseContext()).getWritableDatabase();
        interventionRepository = new InterventionRepository(database);
    }

    public MutableLiveData<List<Intervention>> getInterventions() {
        if (interventions == null){
            interventions = new MutableLiveData<>();
            loadInterventions();
        }
        return interventions;
    }

    public void loadInterventions(){
        List<Entity> entities= new ArrayList<>();
        interventionRepository.findAll(entities);
        List<Intervention> newInterventions = (List<Intervention>)(List<?>)entities;
        interventions.setValue(newInterventions);
    }
}