package com.holcvart.androidptut.model.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Intervention;

import java.util.ArrayList;
import java.util.List;

public class InterventionRepository extends EntityRepository{
    public InterventionRepository(SQLiteDatabase database) {
        super(database);
    }

    public void insert(Intervention intervention){
        ContentValues values = new ContentValues();
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_TITLE, intervention.getTitle());
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_DATE, intervention.getDate());
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_DESCRIPTION, intervention.getDescription());
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_VALID, intervention.isValid());
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_BILLED, intervention.isBilled());
        intervention.setId(database.insert(PhoneRepairManagementContract.Intervention.TABLE_NAME, null, values));
    }

    public Intervention findOneById(long id){
        String selection = PhoneRepairManagementContract.Intervention._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor=database.query
                (
                        PhoneRepairManagementContract.Intervention.TABLE_NAME,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );

        if (!cursor.moveToNext())return null;
        return createIntervention(cursor);
    }

    public List<Intervention> findAll(){
        Cursor cursor = database.query(
                PhoneRepairManagementContract.Intervention.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        List<Intervention> interventions= new ArrayList<>();
        while(cursor.moveToNext()){
            interventions.add(createIntervention(cursor));
        }
        return interventions;
    }

    private Intervention createIntervention(Cursor cursor){
        Intervention intervention= new Intervention();
        intervention.setId(cursor.getLong(cursor.getColumnIndex(PhoneRepairManagementContract.Intervention._ID)));
        intervention.setTitle(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Intervention.COLUMN_NAME_TITLE)));
        intervention.setDate(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Intervention.COLUMN_NAME_DATE)));
        intervention.setDescription(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Intervention.COLUMN_NAME_DESCRIPTION)));
        int isValidInt=cursor.getInt(cursor.getColumnIndex(PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_VALID));
        intervention.setValid(isValidInt != 0);
        int isBilledInt=cursor.getInt(cursor.getColumnIndex(PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_BILLED));
        intervention.setBilled(isBilledInt != 0);
        return intervention;

    }
}
