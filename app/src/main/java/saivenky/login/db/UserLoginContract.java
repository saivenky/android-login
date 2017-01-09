package saivenky.login.db;

import android.provider.BaseColumns;

/**
 * Created by saivenky on 1/8/17.
 */

final class UserLoginContract {
    private UserLoginContract() {}

    public static class UserLogin implements BaseColumns {
        public static final String TABLE_NAME = "user_logins";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_CLIENT_SALT = "client_salt";
        public static final String COLUMN_NAME_SERVER_SALT = "server_salt";
        public static final String COLUMN_NAME_SERVER_HASHED_PASSWORD = "server_hashed_password";
    }

    public static final String SQL_CREATE_USER_LOGINS =
            "CREATE TABLE " + UserLogin.TABLE_NAME + " (" +
                    UserLogin._ID + " INTEGER PRIMARY KEY," +
                    UserLogin.COLUMN_NAME_USERNAME + " TEXT," +
                    UserLogin.COLUMN_NAME_CLIENT_SALT + " BLOB," +
                    UserLogin.COLUMN_NAME_SERVER_SALT + " BLOB," +
                    UserLogin.COLUMN_NAME_SERVER_HASHED_PASSWORD + " BLOB)";

    public static final String SQL_DELETE_USER_LOGINS =
            "DROP TABLE IF EXISTS " + UserLogin.TABLE_NAME;

}
