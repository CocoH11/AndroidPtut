package com.holcvart.androidptut.model.repository;

import android.database.sqlite.SQLiteDatabase;

import com.holcvart.androidptut.model.entity.Entity;

import java.util.List;

public abstract class EntityRepository {
    protected SQLiteDatabase database;
    public EntityRepository(SQLiteDatabase database) {
        this.database = database;
    }
    public abstract void insert(Entity entity);
    public abstract void findOneById(long id, Entity entity);
    public abstract void findAll(List<Entity> entities);
    public abstract void deleteAll();
    public abstract void delete(Entity entity);
    public abstract void update(Entity entity);
}
