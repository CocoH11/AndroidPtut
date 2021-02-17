package com.holcvart.androidptut.view.model;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.database.PhoneRepairManagementDBHelper;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.model.repository.InterventionRepository;

public class EstimateDetailsViewModel extends AndroidViewModel {

    private InterventionRepository interventionRepository;
    private MutableLiveData<Intervention> intervention;

    public EstimateDetailsViewModel(@NonNull Application application) {
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
                PhoneRepairManagementContract.Client.COLUMN_NAME_PHONE,

                PhoneRepairManagementContract.Part._ID,
                PhoneRepairManagementContract.Part.COLUMN_NAME_NAMING,
                PhoneRepairManagementContract.Part.COLUMN_NAME_BILL_PRICE,

                PhoneRepairManagementContract.Need._ID,
                PhoneRepairManagementContract.Need.COLUMN_NAME_QUANTITY

        };
        String[] whereArgs = new String[]{PhoneRepairManagementContract.Intervention._ID};
        String selection = PhoneRepairManagementContract.Intervention.SQL_WHERE(whereArgs);
        String[] selectionArgs = new String[]{String.valueOf(id)};
        String sortOrder = PhoneRepairManagementContract.Intervention.SQL_ORDER_BY_DATE_DESC;
        interventionRepository.find(newIntervention, columns, selection, selectionArgs, sortOrder);
        intervention.setValue(newIntervention);
    }
}
