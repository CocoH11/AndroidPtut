package com.holcvart.androidptut.view.model;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.holcvart.androidptut.model.database.PhoneRepairManagementDBHelper;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.model.repository.InterventionRepository;

import java.util.HashMap;
import java.util.Map;

public class InterventionDetailsViewModel extends AndroidViewModel {
    private InterventionRepository interventionRepository;
    private MutableLiveData<Intervention> intervention;

    public InterventionDetailsViewModel(@NonNull Application application) {
        super(application);
        SQLiteDatabase database = new PhoneRepairManagementDBHelper(application.getBaseContext()).getWritableDatabase();
        interventionRepository = new InterventionRepository(database);
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
        args.put("client", new String[]{"firstName", "name"});
        interventionRepository.findOneById(id, newIntervention, args);
        intervention.setValue(newIntervention);
    }
}
