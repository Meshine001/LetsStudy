package com.meshine.letsstudyclient.db;

import android.provider.BaseColumns;

/**
 * Created by Meshine on 16/5/20.
 */
public final class LetsContract {
    public LetsContract() {
    }

    public static abstract class ContactsEntry implements BaseColumns{
        public static final String TABLE_NAME = "contacts";
        public static final String COLUMN_NAME_CONTACTS_ID = "contacts_id";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_CONTACTS = "contacts";
        public static final String COLUMN_NAME_NICK = "nick";
        public static final String COLUMN_NAME_AVATAR = "avatar";


    }
}
