package com.holcvart.androidptut.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.DATABASE_NAME;
import static com.holcvart.androidptut.model.database.PhoneRepairManagementContract.DATABASE_VERSION;

public class PhoneRepairManagementDBHelper extends SQLiteOpenHelper {

    public PhoneRepairManagementDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PhoneRepairManagementContract.Client.SQL_CREATE_TABLE);
        db.execSQL(PhoneRepairManagementContract.Intervention.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PhoneRepairManagementContract.Client.SQL_DELETE_TABLE);
        db.execSQL(PhoneRepairManagementContract.Client.SQL_DELETE_TABLE);
        onCreate(db);
    }

}
