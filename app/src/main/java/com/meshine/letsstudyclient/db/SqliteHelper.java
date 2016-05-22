package com.meshine.letsstudyclient.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.meshine.letsstudyclient.bean.Contacts;

/**
 * Created by Ming on 2016/5/22.
 */
public class SqliteHelper extends SQLiteOpenHelper {

    public static final String TB_CONTACTS = "t_constacts";
    private static final String TAG = "Database";

    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+
                TB_CONTACTS + "("+
                Contacts.ID + " integer primary key autoincrement,"+
        Contacts.USERNAME + " varchar,"+
        Contacts.CONTACTS+" varchar"+")");
        Log.i(TAG,"onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TB_CONTACTS );
        onCreate(db);
        onCreate(db);
        Log.i(TAG,"onUpgrade");
    }

}
