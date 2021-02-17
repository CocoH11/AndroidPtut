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

import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_DATE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_DESCRIPTION;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_ID_CLIENT;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_BILLED;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_VALID;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_TITLE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.SQL_ORDER_BY_DATE_DESC;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.SQL_WHERE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention._ID;

public class InterventionViewModel extends AndroidViewModel {
    private InterventionRepository interventionRepository;
    private MutableLiveData<List<Intervention>> interventions;

    public InterventionViewModel(Application application) {
        super(application);
        SQLiteDatabase database = new PhoneRepairManagementDBHelper(application.getBaseContext()).getWritableDatabase();
        interventionRepository = new InterventionRepository(database);
    }

    public MutableLiveData<List<Intervention>> getInterventions() {
        if (interventions == null) {
            interventions = new MutableLiveData<>();
            loadInterventions();
        }
        return interventions;
    }

    public void loadInterventions() {
        List<Entity> entities = new ArrayList<>();
        String[] columns = new String[]{_ID, COLUMN_NAME_TITLE, COLUMN_NAME_DATE, COLUMN_NAME_DESCRIPTION, COLUMN_NAME_ID_CLIENT};
        String[] selectionArgs = new String[]{String.valueOf(1)};
        String selection = SQL_WHERE(new String[]{COLUMN_NAME_IS_VALID});
        String order = SQL_ORDER_BY_DATE_DESC;
        interventionRepository.find2(entities, columns, selection, selectionArgs, order);
        List<Intervention> newInterventions = (List<Intervention>) (List<?>) entities;
        interventions.setValue(newInterventions);
    }
}