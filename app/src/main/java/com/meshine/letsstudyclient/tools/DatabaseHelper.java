package com.meshine.letsstudyclient.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.meshine.letsstudyclient.db.LetsContract;

/**
 * Created by Meshine on 16/5/20.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "lets.db"; //数据库名称
    private static final int DATABASE_VERSION = 1; //数据库版本

    private static final String AUTO_INCREMENT = " AUTO_INCREMENT";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_CONTACTS =
            "CREATE TABLE " + LetsContract.ContactsEntry.TABLE_NAME + " (" +
                    LetsContract.ContactsEntry._ID + " INTEGER PRIMARY KEY ," +
                    LetsContract.ContactsEntry.COLUMN_NAME_CONTACTS_ID + INTEGER_TYPE +AUTO_INCREMENT+ COMMA_SEP +
                    LetsContract.ContactsEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                    LetsContract.ContactsEntry.COLUMN_NAME_CONTACTS + TEXT_TYPE + COMMA_SEP+
            " )";

    private static final String SQL_DELETE_CONTACTS =
            "DROP TABLE IF EXISTS " + LetsContract.ContactsEntry.TABLE_NAME;




    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_CONTACTS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
