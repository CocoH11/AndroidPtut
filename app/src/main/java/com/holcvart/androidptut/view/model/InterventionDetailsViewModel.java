package com.holcvart.androidptut.view.model;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.database.PhoneRepairManagementDBHelper;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.model.repository.InterventionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Client.COLUMN_NAME_FIRST_NAME;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_DATE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_DESCRIPTION;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_ID_CLIENT;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_BILLED;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_VALID;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_TITLE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.SQL_WHERE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention._ID;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Client.COLUMN_NAME_NAME;

public class InterventionDetailsViewModel extends AndroidViewModel {
    private InterventionRepository interventionRepository;
    private MutableLiveData<Intervention> intervention;
    private Map<String, String[]> args;
    public InterventionDetailsViewModel(@NonNull Application application) {
        super(application);
        SQLiteDatabase database = new PhoneRepairManagementDBHelper(application.getBaseContext()).getWritableDatabase();
        interventionRepository = new InterventionRepository(database);
        System.out.println(PhoneRepairManagementContract.Intervention.SQL_WHERE(new String[]{_ID, COLUMN_NAME_IS_BILLED}));
        args = new HashMap<>();
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
        String[] columns = new String[]{_ID, COLUMN_NAME_TITLE, COLUMN_NAME_DESCRIPTION, COLUMN_NAME_DATE,
                COLUMN_NAME_DATE, COLUMN_NAME_IS_BILLED, COLUMN_NAME_IS_VALID, COLUMN_NAME_ID_CLIENT,
                PhoneRepairManagementContract.Client._ID, COLUMN_NAME_NAME, COLUMN_NAME_FIRST_NAME, PhoneRepairManagementContract.Part._ID, PhoneRepairManagementContract.Part.COLUMN_NAME_NAMING, PhoneRepairManagementContract.Need._ID, PhoneRepairManagementContract.Need.COLUMN_NAME_QUANTITY};
        String[] whereArgs = new String[]{_ID};
        String selection = SQL_WHERE(whereArgs);
        String[] selectionArgs = new String[]{String.valueOf(id)};
        String sortOrder = PhoneRepairManagementContract.Intervention.SQL_ORDER_BY_DATE_DESC;
        System.out.println(selectionArgs[0]);
        interventionRepository.find(newIntervention, columns, selection, selectionArgs, sortOrder);
        intervention.setValue(newIntervention);
    }
}
