package com.holcvart.androidptut.model.database;

import android.provider.BaseColumns;

public final class PhoneRepairManagementContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PhoneRepairManagement.db";

    private PhoneRepairManagementContract(){}

    public final static class Client implements BaseColumns {
        public final static String TABLE_NAME="client";
        public final static String COLUMN_NAME_FIRST_NAME="firstName";
        public final static String COLUMN_NAME_NAME="name";
        public final static String COLUMN_NAME_EMAIL="email";
        public final static String COLUMN_NAME_PHONE="phone";
        public final static String COLUMN_NAME_ADDRESS="address";

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
                "DELETE TABLE IF EXISTS "+TABLE_NAME
                ;

    }
}
