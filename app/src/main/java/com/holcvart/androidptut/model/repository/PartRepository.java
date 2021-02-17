package com.holcvart.androidptut.model.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.model.entity.Part;

import java.util.List;
import java.util.Map;

import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_DATE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_DESCRIPTION;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_BILLED;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_IS_VALID;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention.COLUMN_NAME_TITLE;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.Intervention._ID;

public class PartRepository extends EntityRepository{
    public PartRepository(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public void insert(Entity entity) {
        Part part = (Part)entity;
        ContentValues values = new ContentValues();
        values.put(PhoneRepairManagementContract.Part.COLUMN_NAME_NAMING, part.getNaming());
        values.put(PhoneRepairManagementContract.Part.COLUMN_NAME_DEAL_PRICE, part.getDealPrice());
        values.put(PhoneRepairManagementContract.Part.COLUMN_NAME_BILL_PRICE, part.getBillPrice());
        values.put(PhoneRepairManagementContract.Part.COLUMN_NAME_QUANTITY, part.getQuantity());
        part.setId(database.insert(PhoneRepairManagementContract.Part.TABLE_NAME, null, values));
    }

    @Override
    public void findOneById(long id, Entity entity, Map<String, String[]> args) {
        String selection = PhoneRepairManagementContract.Part._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Log.d("part id ", String.valueOf(id));
        Cursor cursor = database.query(PhoneRepairManagementContract.Part.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (!cursor.moveToNext())return;
        createPart(cursor, (Part)entity);
    }

    @Override
    public void findAll(List<Entity> entities) {

    }

    public void find2(List<Entity> entities, String[] columns, String selection, String[] selectionArgs, String sortOrder){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(PhoneRepairManagementContract.Part.TABLE_NAME);
        Cursor cursor= queryBuilder.query(database, columns, selection, selectionArgs, null, null, sortOrder);
        while(cursor.moveToNext()){
            Part part= new Part();
            makePart(cursor, part);
            entities.add(part);
        }
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void delete(Entity entity) {

    }

    @Override
    public void update(Entity entity) {

    }

    private void createPart(Cursor cursor, Part part) {
        part.setId(cursor.getLong(cursor.getColumnIndex(PhoneRepairManagementContract.Part._ID)));
        part.setNaming(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_NAMING)));
        part.setDealPrice(cursor.getDouble(cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_DEAL_PRICE)));
        part.setBillPrice(cursor.getDouble(cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_BILL_PRICE)));
        part.setQuantity(cursor.getInt(cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_QUANTITY)));
    }

    public void makePart(Cursor cursor, Part part){
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Part._ID) != -1)part.setId(cursor.getLong(cursor.getColumnIndex(PhoneRepairManagementContract.Part._ID)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_NAMING) != -1)part.setNaming(cursor.getString(cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_NAMING)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_QUANTITY) != -1)part.setQuantity(cursor.getInt(cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_QUANTITY)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_DEAL_PRICE) != -1)part.setDealPrice(cursor.getDouble(cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_DEAL_PRICE)));
        if (cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_BILL_PRICE) != -1)part.setBillPrice(cursor.getDouble(cursor.getColumnIndex(PhoneRepairManagementContract.Part.COLUMN_NAME_BILL_PRICE)));
    }
}
