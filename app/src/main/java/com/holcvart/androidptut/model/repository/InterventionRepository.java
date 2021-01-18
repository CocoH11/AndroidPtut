package com.holcvart.androidptut.model.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.model.entity.PartsNeeded;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_DATE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_DESCRIPTION;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_ID_CLIENT;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_BILLED;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_VALID;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_TITLE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.TABLE_NAME;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention._ID;

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
        if (intervention.getClient() != null)values.put(COLUMN_NAME_ID_CLIENT, intervention.getClient().getId());
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
    public void findOneById(long id, Entity entity, Map<String, String[]> args) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String selection = PhoneRepairManagementContract.Intervention._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        String tables = TABLE_NAME;
        List<String> projectionInList = new ArrayList<String>();
        projectionInList.add(_ID);
        projectionInList.add(COLUMN_NAME_TITLE);
        projectionInList.add(COLUMN_NAME_DESCRIPTION);
        projectionInList.add(COLUMN_NAME_DATE);
        projectionInList.add(COLUMN_NAME_IS_BILLED);
        projectionInList.add(COLUMN_NAME_IS_VALID);
        if (args != null && args.get("client").length != 0){
            tables += " INNER JOIN " + PhoneRepairManagementContract.Client.TABLE_NAME + " ON (" + COLUMN_NAME_ID_CLIENT + " = " + PhoneRepairManagementContract.Client._ID + ")";
            projectionInList.add(COLUMN_NAME_ID_CLIENT);
            for (int i = 0; i < args.get("client").length; i++) {
                String[] clientsArgs = args.get("client");
                switch (clientsArgs[i]){
                    case "firstName":
                        projectionInList.add(PhoneRepairManagementContract.Client.COLUMN_NAME_FIRST_NAME);
                        break;
                    case "name":
                        projectionInList.add(PhoneRepairManagementContract.Client.COLUMN_NAME_NAME);
                        break;
                }
            }
        }
        System.out.println(tables);
        queryBuilder.setTables(tables);
        String[] projectionIn = new String[projectionInList.size()];
        for (int i = 0; i < projectionInList.size(); i++)projectionIn[i] = projectionInList.get(i);
        Cursor cursor= queryBuilder.query(database, projectionIn, selection, selectionArgs, null, null, null);
        if (!cursor.moveToNext())return;
        createIntervention(cursor, (Intervention)entity);
    }

    @Override
    public void findAll(List<Entity> entities) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(PhoneRepairManagementContract.Intervention.TABLE_NAME + " INNER JOIN " + PhoneRepairManagementContract.Client.TABLE_NAME + " ON (" + COLUMN_NAME_ID_CLIENT + " = " + PhoneRepairManagementContract.Client._ID + ")");
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
        values.put(COLUMN_NAME_ID_CLIENT, intervention.getClient().getId());
        int result = database.update(PhoneRepairManagementContract.Intervention.TABLE_NAME, values, PhoneRepairManagementContract.Intervention._ID + " = ?", new String[]{String.valueOf(intervention.getId())});
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
        intervention.setClient(new Client());
        System.out.println("Interve,ntion id: "+intervention.getId());
    }
}
