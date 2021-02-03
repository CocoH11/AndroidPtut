package com.holcvart.androidptut.model.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.model.entity.Part;
import com.holcvart.androidptut.model.entity.PartsNeeded;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_DATE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_DESCRIPTION;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_ID_CLIENT;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_BILLED;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_VALID;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_TITLE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.SQL_TABLE_JOIN_CLIENT;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.SQL_TABLE_LEFT_JOIN_ALL;
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
        String selection = PhoneRepairManagementContract.Intervention._ID + " = ? ";
        String[] selectionArgs = { String.valueOf(id) };
        String tables = SQL_TABLE_JOIN_CLIENT;
        queryBuilder.setTables(tables);
        Cursor cursor= queryBuilder.query(database, null, selection, selectionArgs, null, null, null);
        if (cursor.getCount() == 0){
            queryBuilder.setTables(TABLE_NAME);
            cursor = queryBuilder.query(database, null, selection, selectionArgs, null, null, null);
        }
        if (!cursor.moveToNext())return;
        createIntervention(cursor, (Intervention)entity);
        if (cursor.getColumnIndex(COLUMN_NAME_ID_CLIENT) != -1 && cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_ID_CLIENT)) > 0){
            Client client = new Client();
            createClient(cursor, client);
            ((Intervention) entity).setClient(client);
        }
    }

    public void find(Entity entity, String[] columns, String selection, String[] selectionArgs, String sortOrder){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String tables = SQL_TABLE_LEFT_JOIN_ALL;
        queryBuilder.setTables(tables);
        Cursor cursor = queryBuilder.query(database, columns, selection, selectionArgs, null, null, sortOrder);
        if (cursor.getCount() == 0)return;
        cursor.moveToNext();
        if (cursor.getColumnIndex(_ID) != -1)makeIntervention(cursor, (Intervention)entity);
        Client client = new Client();
        if (cursor.getColumnIndex(COLUMN_NAME_ID_CLIENT) != -1)makeClient(cursor, client);
        ((Intervention) entity).setClient(client);
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Need._ID) != -1){
            List<PartsNeeded> partsNeededList = new ArrayList<>();
            do{
                System.out.println("parts");
                Part part = new Part();
                PartsNeeded partsNeeded = new PartsNeeded();
                makeParts(cursor, part, partsNeeded);
                partsNeededList.add(partsNeeded);
            }while(cursor.moveToNext());
            ((Intervention) entity).setPartsNeededs(partsNeededList);
        }
    }

    public void makeIntervention(Cursor cursor, Intervention intervention){
        if (cursor.getColumnIndex(_ID) != -1)intervention.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        if (cursor.getColumnIndex(COLUMN_NAME_TITLE) != -1)intervention.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE)));
        if (cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION) != -1)intervention.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DESCRIPTION)));
        if (cursor.getColumnIndex(COLUMN_NAME_DATE) != -1)intervention.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATE)));
        if (cursor.getColumnIndex(COLUMN_NAME_IS_BILLED) != -1)intervention.setBilled(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_IS_BILLED)) == 1);
        if (cursor.getColumnIndex(COLUMN_NAME_IS_VALID) != -1)intervention.setValid(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_IS_VALID)) == 1);
    }

    public void makeClient(Cursor cursor, Client client){
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Client._ID) != -1)client.setId(cursor.getLong(cursor.getColumnIndex(PhoneRepairManagementContract.Client._ID)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_NAME) != -1)client.setName(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_NAME)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_FIRST_NAME) != -1)client.setFirstName(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_FIRST_NAME)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_EMAIL) != -1)client.setEmail(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_EMAIL)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_PHONE) != -1)client.setPhone(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_PHONE)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_ADDRESS) != -1)client.setAddress(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_ADDRESS)));
    }

    public void makeParts(Cursor cursor, Part part, PartsNeeded partsNeeded){
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Part._ID) != -1)part.setId(cursor.getLong(cursor.getColumnIndex(PhoneRepairManagementContract.Part._ID)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_NAMING) != -1)part.setNaming(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_NAMING)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_QUANTITY) != -1)part.setQuantity(cursor.getInt(cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_QUANTITY)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_BILL_PRICE) != -1)part.setBillPrice(cursor.getDouble(cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_BILL_PRICE)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_TYPE_DEAL_PRICE) != -1)part.setDealPrice(cursor.getDouble(cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_DEAL_PRICE)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Need._ID) != -1)partsNeeded.setId(cursor.getLong(cursor.getColumnIndex(PhoneRepairManagementContract.Need._ID)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Need.COLUMN_NAME_QUANTITY) != -1)partsNeeded.setQuantity(cursor.getInt(cursor.getColumnIndex(PhoneRepairManagementContract.Need.COLUMN_NAME_QUANTITY)));
        partsNeeded.setPart(part);
    }

    @Override
    public void findAll(List<Entity> entities) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(PhoneRepairManagementContract.Intervention.TABLE_NAME);
        Cursor cursor= queryBuilder.query(database, null, null, null, null, null, PhoneRepairManagementContract.Intervention.SQL_ORDER_BY_DATE_DESC);
        while(cursor.moveToNext()){
            Intervention intervention= new Intervention();
            createIntervention(cursor, intervention);
            entities.add(intervention);
        }
    }

    public void find2(List<Entity> entities, String[] columns, String selection, String[] selectionArgs, String sortOrder){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(PhoneRepairManagementContract.Intervention.TABLE_NAME);
        Cursor cursor= queryBuilder.query(database, columns, selection, selectionArgs, null, null, PhoneRepairManagementContract.Intervention.SQL_ORDER_BY_DATE_DESC);
        while(cursor.moveToNext()){
            Intervention intervention= new Intervention();
            makeIntervention(cursor, intervention);
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
    }

    private void createClient(Cursor cursor, Client client){
        client.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_ID_CLIENT)));
        client.setName(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_NAME)));
        client.setFirstName(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Client.COLUMN_NAME_FIRST_NAME)));
    }

    private void getIntervention(Intervention intervention, String[] interventionArgs, String selection, String[] selectionArgs){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        queryBuilder.query(database, interventionArgs, selection, selectionArgs, null, null, null);
    }
}
