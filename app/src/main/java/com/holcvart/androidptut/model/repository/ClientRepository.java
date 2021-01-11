package com.holcvart.androidptut.model.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.ArrayMap;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.Intervention;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRepository extends EntityRepository{
    public ClientRepository(SQLiteDatabase database) {
        super(database);
    }

    public void insert(Entity entity){
        Client client= (Client)entity;
        ContentValues values = new ContentValues();
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_FIRST_NAME, client.getFirstName());
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_NAME, client.getName());
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_EMAIL, client.getEmail());
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_PHONE, client.getPhone());
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_ADDRESS, client.getAddress());
        client.setId(database.insert(PhoneRepairManagementContract.Client.TABLE_NAME, null, values));
    }

    public void findOneById(long id, Entity entity){
        String selection = PhoneRepairManagementContract.Client._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor=database.query
                (
                        PhoneRepairManagementContract.Client.TABLE_NAME,
                        null,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );

        if (!cursor.moveToNext())return;
        createClient(cursor, (Client)entity);
    }

    public void findAll(List<Entity> entities){
        Cursor cursor = database.query(
                PhoneRepairManagementContract.Client.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        while(cursor.moveToNext()){
            Client client= new Client();
            createClient(cursor, client);

            entities.add(client);
        }
    }

    @Override
    public void deleteAll() {
        database.delete(PhoneRepairManagementContract.Client.TABLE_NAME, null, null);
    }

    @Override
    public void delete(Entity entity) {
        Client client = (Client)entity;
        database.delete(PhoneRepairManagementContract.Intervention.TABLE_NAME,
                PhoneRepairManagementContract.Intervention._ID + " = ?",
                new String[]{String.valueOf(client.getId())});
        client.setId(-1);
    }

    @Override
    public void update(Entity entity) {
        Client client = (Client)entity;
        ContentValues values = new ContentValues();
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_FIRST_NAME, client.getFirstName());
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_NAME, client.getName());
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_EMAIL, client.getEmail());
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_PHONE, client.getPhone());
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_ADDRESS, client.getAddress());
        database.update(PhoneRepairManagementContract.Client.TABLE_NAME, values, PhoneRepairManagementContract.Client._ID, new String[]{String.valueOf(client.getId())});
    }


    protected void createClient(Cursor cursor, Client client){
        client.setId(cursor.getLong(cursor.getColumnIndex(PhoneRepairManagementContract.Client._ID)));
        client.setFirstName(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_FIRST_NAME)));
        client.setName(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_NAME)));
        client.setEmail(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_EMAIL)));
        client.setPhone(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_PHONE)));
        client.setAddress(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_ADDRESS)));
    }
}
