package com.holcvart.androidptut.model.database;

import android.provider.BaseColumns;

public final class PhoneRepairManagementContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PhoneRepairManagement.db";

    private PhoneRepairManagementContract(){}

    public final static class Client implements BaseColumns {
        public final static String TABLE_NAME = "client";
        public final static String _ID = TABLE_NAME + "_" + BaseColumns._ID;
        public final static String COLUMN_NAME_FIRST_NAME = TABLE_NAME + "_" + "firstName";
        public final static String COLUMN_NAME_NAME = TABLE_NAME + "_" + "name";
        public final static String COLUMN_NAME_EMAIL = TABLE_NAME + "_" + "email";
        public final static String COLUMN_NAME_PHONE = TABLE_NAME + "_" + "phone";
        public final static String COLUMN_NAME_ADDRESS = TABLE_NAME + "_" + "address";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE "+TABLE_NAME+"("+
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_NAME + " TEXT," +
                        COLUMN_NAME_FIRST_NAME + " TEXT," +
                        COLUMN_NAME_EMAIL + " TEXT," +
                        COLUMN_NAME_PHONE + " TEXT," +
                        COLUMN_NAME_ADDRESS + " TEXT);"
                ;

        public static final String SQL_DELETE_TABLE =
                "DELETE TABLE IF EXISTS " + TABLE_NAME + ";"
                ;
    }

    public final static class Intervention implements BaseColumns{
        public final static String TABLE_NAME = "intervention";
        public final static String _ID = TABLE_NAME + "_" + BaseColumns._ID;
        public final static String COLUMN_NAME_TITLE = TABLE_NAME + "_" + "title";
        public final static String COLUMN_NAME_DATE = TABLE_NAME + "_" + "date";
        public final static String COLUMN_NAME_DESCRIPTION = TABLE_NAME + "_" + "description";
        public final static String COLUMN_NAME_IS_VALID = TABLE_NAME + "_" + "isValid";
        public final static String COLUMN_NAME_IS_BILLED = TABLE_NAME + "_" + "isBilled";
        public final static String COLUMN_NAME_ID_CLIENT= TABLE_NAME + "_" + "idClient";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE "+TABLE_NAME+"("+
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_TITLE + " TEXT," +
                        COLUMN_NAME_DATE + " TEXT," +
                        COLUMN_NAME_DESCRIPTION + " TEXT," +
                        COLUMN_NAME_IS_BILLED + " INTEGER," +
                        COLUMN_NAME_IS_VALID + " INTEGER," +
                        COLUMN_NAME_ID_CLIENT + " INTEGER," +
                        "CONSTRAINT " + TABLE_NAME + "_" + Client.TABLE_NAME + "_FK FOREIGN KEY (" + COLUMN_NAME_ID_CLIENT + ") " +
                        "REFERENCES "+ Client.TABLE_NAME +"("+Client._ID+")" +
                        ");"
                ;

        public static final String SQL_DELETE_TABLE =
                "DELETE TABLE IF EXISTS " + TABLE_NAME + ";"
                ;
    }
}


