package com.holcvart.androidptut.model.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.entity.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository {
    private final SQLiteDatabase database;

    public ClientRepository(SQLiteDatabase database) {
        this.database = database;
    }

    public void insert(Client client){
        ContentValues values = new ContentValues();
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_FIRST_NAME, client.getFirstName());
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_NAME, client.getName());
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_EMAIL, client.getEmail());
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_PHONE, client.getPhone());
        values.put(PhoneRepairManagementContract.Client.COLUMN_NAME_ADDRESS, client.getAddress());
        client.setId(database.insert(PhoneRepairManagementContract.Client.TABLE_NAME, null, values));
    }

    public Client findOneById(int id){
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

        if (!cursor.moveToNext())return null;
        return createClient(cursor);
    }

    public List<Client> findAll(){
        Cursor cursor = database.query(
                PhoneRepairManagementContract.Client.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        List<Client> clients= new ArrayList<>();
        while(cursor.moveToNext()){
            clients.add(createClient(cursor));
        }
        return clients;
    }

    private Client createClient(Cursor cursor){
        Client client= new Client();
        client.setId(cursor.getLong(cursor.getColumnIndex(PhoneRepairManagementContract.Client._ID)));
        client.setFirstName(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_FIRST_NAME)));
        client.setName(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_NAME)));
        client.setEmail(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_EMAIL)));
        client.setPhone(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_PHONE)));
        client.setAddress(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_ADDRESS)));
        return client;
    }
}
