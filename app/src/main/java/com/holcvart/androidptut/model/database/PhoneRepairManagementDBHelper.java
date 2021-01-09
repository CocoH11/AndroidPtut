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
        db.execSQL(PhoneRepairManagementContract.Part.SQL_CREATE_TABLE);
        db.execSQL(PhoneRepairManagementContract.Provider.SQL_CREATE_TABLE);
        db.execSQL(PhoneRepairManagementContract.Deal.SQL_CREATE_TABLE);
        db.execSQL(PhoneRepairManagementContract.Need.SQL_CREATE_TABLE);
        db.execSQL(PhoneRepairManagementContract.Store.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);
    }

    public void dropTables(SQLiteDatabase db){
        db.execSQL(PhoneRepairManagementContract.Store.SQL_DROP_TABLE);
        db.execSQL(PhoneRepairManagementContract.Need.SQL_DROP_TABLE);
        db.execSQL(PhoneRepairManagementContract.Deal.SQL_DROP_TABLE);
        db.execSQL(PhoneRepairManagementContract.Provider.SQL_DROP_TABLE);
        db.execSQL(PhoneRepairManagementContract.Part.SQL_DROP_TABLE);
        db.execSQL(PhoneRepairManagementContract.Intervention.SQL_DROP_TABLE);
        db.execSQL(PhoneRepairManagementContract.Client.SQL_DROP_TABLE);
    }

}
