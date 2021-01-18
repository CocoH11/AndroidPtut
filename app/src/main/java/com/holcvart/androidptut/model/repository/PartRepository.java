package com.holcvart.androidptut.model.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.Part;

import java.util.List;
import java.util.Map;

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
        Cursor cursor = database.query(PhoneRepairManagementContract.Part.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (!cursor.moveToNext())return;
        createPart(cursor, (Part)entity);
    }

    @Override
    public void findAll(List<Entity> entities) {

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
}
