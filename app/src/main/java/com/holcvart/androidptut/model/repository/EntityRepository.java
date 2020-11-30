package com.holcvart.androidptut.model.repository;

import android.database.sqlite.SQLiteDatabase;

public abstract class EntityRepository {
    protected SQLiteDatabase database;

    public EntityRepository(SQLiteDatabase database) {
        this.database = database;
    }
}
