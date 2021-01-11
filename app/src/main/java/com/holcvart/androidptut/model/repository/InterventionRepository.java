package com.holcvart.androidptut.model.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.model.entity.PartsNeeded;

import java.util.List;
import java.util.Random;

import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_ID_CLIENT;

public class InterventionRepository extends EntityRepository{
    public InterventionRepository(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public void insert(Entity entity) {
        Intervention intervention=(Intervention)entity;
        ContentValues values = new ContentValues();
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_TITLE, intervention.getTitle());
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_DATE, intervention.getDate());
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_DESCRIPTION, intervention.getDescription());
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_VALID, intervention.isValid());
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_BILLED, intervention.isBilled());
        intervention.setId(database.insert(PhoneRepairManagementContract.Intervention.TABLE_NAME, null, values));

        for (PartsNeeded partsNeeded:intervention.getPartsNeededs()) {
            ContentValues partValues = new ContentValues();
            partValues.put(PhoneRepairManagementContract.Need.COLUMN_NAME_QUANTITY, partsNeeded.getQuantity());
            partValues.put(PhoneRepairManagementContract.Need.COLUMN_NAME_ID_INTERVENTION, intervention.getId());
            partsNeeded.setId(database.insert(PhoneRepairManagementContract.Need.TABLE_NAME, null, partValues));
            partsNeeded.setIntervention(intervention);
        }
    }

    @Override
    public void findOneById(long id, Entity entity) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(PhoneRepairManagementContract.Intervention.TABLE_NAME + " INNER JOIN " + PhoneRepairManagementContract.Client.TABLE_NAME + " ON " + COLUMN_NAME_ID_CLIENT + " = " + PhoneRepairManagementContract.Client._ID);
        Cursor cursor= queryBuilder.query(database, null, null, null, null, null, null);
        if (!cursor.moveToNext())return;
        createIntervention(cursor, (Intervention)entity);
    }

    @Override
    public void findAll(List<Entity> entities) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(PhoneRepairManagementContract.Intervention.TABLE_NAME/* + " INNER JOIN " + PhoneRepairManagementContract.Client.TABLE_NAME + " ON " + COLUMN_NAME_ID_CLIENT + " = " + PhoneRepairManagementContract.Client._ID*/);
        Cursor cursor= queryBuilder.query(database, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            Intervention intervention= new Intervention();
            createIntervention(cursor, intervention);
            entities.add(intervention);
        }
    }

    @Override
    public void deleteAll() {
        database.delete(PhoneRepairManagementContract.Intervention.TABLE_NAME, null, null);
    }

    @Override
    public void delete(Entity entity) {
        Intervention intervention=(Intervention)entity;
        database.delete(PhoneRepairManagementContract.Intervention.TABLE_NAME,
                PhoneRepairManagementContract.Intervention._ID + " = ?",
                new String[]{String.valueOf(intervention.getId())});
        intervention.setId(-1);
    }

    @Override
    public void update(Entity entity) {
        Intervention intervention = (Intervention)entity;
        ContentValues values= new ContentValues();
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_TITLE, intervention.getTitle());
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_DATE, intervention.getDate());
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_DESCRIPTION, intervention.getDescription());
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_VALID, intervention.isValid());
        values.put(PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_BILLED, intervention.isBilled());
        database.update(PhoneRepairManagementContract.Intervention.TABLE_NAME, values, PhoneRepairManagementContract.Intervention._ID + " = ?", new String[]{String.valueOf(intervention.getId())});
    }

    private void createIntervention(Cursor cursor, Intervention intervention){
        intervention.setId(cursor.getLong(cursor.getColumnIndex(PhoneRepairManagementContract.Intervention._ID)));
        intervention.setTitle(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Intervention.COLUMN_NAME_TITLE)));
        intervention.setDate(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Intervention.COLUMN_NAME_DATE)));
        intervention.setDescription(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Intervention.COLUMN_NAME_DESCRIPTION)));
        int isValidInt=cursor.getInt(cursor.getColumnIndex(PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_VALID));
        intervention.setValid(isValidInt != 0);
        int isBilledInt=cursor.getInt(cursor.getColumnIndex(PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_BILLED));
        intervention.setBilled(isBilledInt != 0);
    }
}
