package com.holcvart.androidptut.model.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.Intervention;

import java.util.List;

import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_ID_CLIENT;

public class InterventionRepository extends EntityRepository{
    private ClientRepository clientRepository;

    public InterventionRepository(SQLiteDatabase database) {
        super(database);
        clientRepository = new ClientRepository(database);
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
        if (intervention.getClient().getId()==-1)clientRepository.insert(intervention.getClient());
        values.put(COLUMN_NAME_ID_CLIENT, intervention.getClient().getId());
        intervention.setId(database.insert(PhoneRepairManagementContract.Intervention.TABLE_NAME, null, values));
    }

    @Override
    public void findOneById(long id, Entity entity) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(PhoneRepairManagementContract.Intervention.TABLE_NAME + " INNER JOIN " + PhoneRepairManagementContract.Client.TABLE_NAME + " ON " + COLUMN_NAME_ID_CLIENT + " = " + PhoneRepairManagementContract.Client._ID);
        Cursor cursor= queryBuilder.query(database, null, null, null, null, null, null);
        if (!cursor.moveToNext())return;
        createIntervention(cursor, (Intervention)entity);
    }

    public void findAll(List<Entity> entities) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(PhoneRepairManagementContract.Intervention.TABLE_NAME + " INNER JOIN " + PhoneRepairManagementContract.Client.TABLE_NAME + " ON " + COLUMN_NAME_ID_CLIENT + " = " + PhoneRepairManagementContract.Client._ID);
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
        Client client= new Client();
        clientRepository.createClient(cursor, client);
        intervention.setClient(client);
    }
}
