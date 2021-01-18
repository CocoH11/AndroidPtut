package com.holcvart.androidptut.model.repository;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.entity.Deal;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.PartsStored;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class DealRepository extends EntityRepository{
    public DealRepository(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public void insert(Entity entity) {
        Deal deal = (Deal)entity;
        ContentValues values = new ContentValues();
        values.put(PhoneRepairManagementContract.Deal.COLUMN_NAME_DATE, deal.getDate());
        values.put(PhoneRepairManagementContract.Deal.COLUMN_NAME_ID_PROVIDER, deal.getProvider().getId());
        deal.setId(database.insert(PhoneRepairManagementContract.Deal.TABLE_NAME, null, values));

        for (PartsStored partsStored:deal.getDeals()) {
            ContentValues partValues = new ContentValues();
            partValues.put(PhoneRepairManagementContract.Store.COLUMN_NAME_QUANTITY, partsStored.getQuantity());
            partValues.put(PhoneRepairManagementContract.Store.COLUMN_NAME_ID_PART, partsStored.getPart().getId());
            partsStored.setId(database.insert(PhoneRepairManagementContract.Store.TABLE_NAME, null, partValues));
            partsStored.setDeal(deal);
        }
    }

    @Override
    public void findOneById(long id, Entity entity, Map<String, String[]> args) {

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
}
