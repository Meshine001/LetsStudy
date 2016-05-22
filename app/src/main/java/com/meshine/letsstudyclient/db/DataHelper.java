package com.meshine.letsstudyclient.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.meshine.letsstudyclient.bean.Contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 2016/5/22.
 */
public class DataHelper {
    private static final String TAG = DataHelper.class.getName();

    private static String DB_NAME = "lets.db";
    //数据库版本
    private static int DB_VERSION = 1;
    private SQLiteDatabase db;
    private SqliteHelper dbHelper;

    public DataHelper(Context context){
        dbHelper = new SqliteHelper(context, DB_NAME, null, DB_VERSION );
        db = dbHelper.getWritableDatabase();
    }

    public void Close() {
        db.close();
        dbHelper.close();
    }
    /**
     * 获取本地联系人
     * @return
     */
    public List<Contacts> getContactsList(String username){
        List<Contacts> contactsList = new ArrayList<>();
        String selections = "username=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(SqliteHelper.TB_CONTACTS, null, selections , selectionArgs, null,
                null, Contacts.ID + " DESC");

        while (cursor.moveToNext()) {
            Contacts c = new Contacts(cursor.getString(0),cursor.getString(1),cursor.getString(2));
            contactsList.add(c);
        }
        cursor.close();
        Log.i(TAG,"Get contacts");
        return contactsList;
    }

    /**
     * 添加联系人
     * @param contacts
     * @return
     */
    public Long addContacts(Contacts contacts){
        ContentValues values = new ContentValues();
        values.put(Contacts.USERNAME,contacts.getUsername());
        values.put(Contacts.CONTACTS,contacts.getContacts());
        Long id = db.insert(SqliteHelper.TB_CONTACTS,null,values);
        Log.i(TAG,"Add contacts");
        return id;
    }
}
