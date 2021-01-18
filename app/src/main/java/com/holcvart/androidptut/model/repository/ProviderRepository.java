package com.holcvart.androidptut.model.repository;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.Provider;

import java.util.List;
import java.util.Map;

public class ProviderRepository extends EntityRepository{
    public ProviderRepository(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public void insert(Entity entity) {
        Provider provider = (Provider)entity;
        ContentValues values = new ContentValues();
        values.put(PhoneRepairManagementContract.Provider.COLUMN_NAME_NAMING, provider.getNaming());
        provider.setId(database.insert(PhoneRepairManagementContract.Provider.TABLE_NAME, null, values));
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
