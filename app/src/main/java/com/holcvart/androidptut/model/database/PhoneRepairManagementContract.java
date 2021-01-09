package com.holcvart.androidptut.model.database;

import android.provider.BaseColumns;
import android.widget.TableLayout;

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

        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";"
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

        public static final String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";"
                ;
    }

    public static final class Part implements BaseColumns{
        public final static String TABLE_NAME="part";
        public final static String _ID=TABLE_NAME+"_"+BaseColumns._ID;
        public final static String COLUMN_NAME_NAMING=TABLE_NAME+"_"+"naming";
        public final static String COLUMN_NAME_DEAL_PRICE=TABLE_NAME+"_"+"dealPrice";
        public final static String COLUMN_NAME_BILL_PRICE=TABLE_NAME+"_"+"billPrice";
        public static final String COLUMN_NAME_QUANTITY = TABLE_NAME+"_"+"quantity";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE "+TABLE_NAME+"("+
                        _ID+" INTEGER PRIMARY KEY," +
                        COLUMN_NAME_NAMING+" TEXT,"+
                        COLUMN_NAME_DEAL_PRICE+" REAL,"+
                        COLUMN_NAME_BILL_PRICE+" REAL,"+
                        COLUMN_NAME_QUANTITY+" INTEGER"+
                        ");"
                ;
        public final static String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";"
                ;
    }

    public static final class Provider implements BaseColumns{
        public final static String TABLE_NAME="provider";
        public final static String _ID=TABLE_NAME+"_"+BaseColumns._ID;
        public final static String COLUMN_NAME_NAMING=TABLE_NAME+"_"+"naming";

        public final static String SQL_CREATE_TABLE=
                "CREATE TABLE "+TABLE_NAME+"("+
                        _ID+" INTEGER PRIMARY KEY," +
                        COLUMN_NAME_NAMING+" TEXT"+
                        ");"
                ;
        public final static String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";"
                ;
    }

    public static final class Deal implements BaseColumns{
        public final static String TABLE_NAME="deal";
        public final static String _ID=TABLE_NAME+"_"+BaseColumns._ID;
        public final static String COLUMN_NAME_DATE=TABLE_NAME+"_"+"date";
        public final static String COLUMN_NAME_ID_INTERVENTION=TABLE_NAME+"_"+"idIntervention";
        public final static String COLUMN_NAME_ID_PROVIDER=TABLE_NAME+"_"+"idProvider";

        public final static String SQL_CREATE_TABLE=
                "CREATE TABLE "+TABLE_NAME+"("+
                        _ID+" INTEGER PRIMARY KEY," +
                        COLUMN_NAME_DATE+" TEXT,"+
                        COLUMN_NAME_ID_INTERVENTION+" INTEGER,"+
                        COLUMN_NAME_ID_PROVIDER+" INTEGER,"+
                        "CONSTRAINT " + TABLE_NAME + "_" + Intervention.TABLE_NAME + "_FK FOREIGN KEY (" + COLUMN_NAME_ID_INTERVENTION + ") " +
                        "REFERENCES "+ Intervention.TABLE_NAME +"("+Intervention._ID+"),"+
                        "CONSTRAINT "+ TABLE_NAME + "_" + Provider.TABLE_NAME + "_FK FOREIGN KEY (" + COLUMN_NAME_ID_PROVIDER +")"+
                        "REFERENCES "+Provider.TABLE_NAME+"("+Provider._ID+")"+
                        ");"
                ;
        public final static String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";"
                ;

    }

    public static final class Need implements BaseColumns{
        public final static String TABLE_NAME="need";
        public final static String _ID=TABLE_NAME+"_"+BaseColumns._ID;
        public final static String COLUMN_NAME_QUANTITY=TABLE_NAME+"_"+"quantity";
        public final static String COLUMN_NAME_ID_INTERVENTION=TABLE_NAME+"_"+"idIntervention";

        public final static String SQL_CREATE_TABLE=
                "CREATE TABLE "+TABLE_NAME+"("+
                        _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        COLUMN_NAME_ID_INTERVENTION+" INTEGER,"+
                        COLUMN_NAME_QUANTITY+" INTEGER,"+
                        "UNIQUE("+_ID+","+COLUMN_NAME_ID_INTERVENTION+"),"+
                        "CONSTRAINT "+TABLE_NAME+"_"+Part.TABLE_NAME+"_FK FOREIGN KEY ("+_ID+")"+
                        "REFERENCES " + Part.TABLE_NAME+"("+Part._ID+"),"+
                        "CONSTRAINT "+TABLE_NAME+"_"+Intervention.TABLE_NAME+"_FK FOREIGN KEY ("+COLUMN_NAME_ID_INTERVENTION+")"+
                        "REFERENCES "+Intervention.TABLE_NAME+"("+Intervention._ID+")"+
                        ");"
                ;
        public final static String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";"
                ;
    }

    public static final class Store implements BaseColumns{
        public final static String TABLE_NAME="store";
        public final static String _ID=TABLE_NAME+"_"+BaseColumns._ID;
        public final static String COLUMN_NAME_QUANTITY=TABLE_NAME+"_"+"quantity";
        public final static String COLUMN_NAME_ID_PART=TABLE_NAME+"_"+"idPart";

        public final static String SQL_CREATE_TABLE=
                "CREATE TABLE "+TABLE_NAME+"("+
                        _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                        COLUMN_NAME_ID_PART+" INTEGER,"+
                        COLUMN_NAME_QUANTITY+" INTEGER,"+
                        "UNIQUE("+_ID+","+COLUMN_NAME_ID_PART+"),"+
                        "CONSTRAINT "+TABLE_NAME+Deal.TABLE_NAME+"_FK FOREIGN KEY("+_ID+")"+
                        "REFERENCES "+Deal.TABLE_NAME+"("+Deal._ID+"),"+
                        "CONSTRAINT "+TABLE_NAME+Part.TABLE_NAME+"0"+"_FK FOREIGN KEY ("+COLUMN_NAME_ID_PART+")"+
                        "REFERENCES "+Part.TABLE_NAME+"("+Part._ID+")"+
                        ");"
                ;

        public final static String SQL_DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME + ";"
                ;
        ;
    }
}


