package saivenky.login.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by saivenky on 1/8/17.
 */

public class UserLoginDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserLogin.db";
    private static UserLoginDbHelper SINGLETON;

    public static void initialize(Context context) {
        SINGLETON = new UserLoginDbHelper(context);
    }

    public static UserLoginDbHelper getInstance() {
        if(SINGLETON == null) {
            throw new NullPointerException("UserLoginDbHelper not initialized");
        }

        return SINGLETON;
    }

    private UserLoginDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserLoginContract.SQL_CREATE_USER_LOGINS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(UserLoginContract.SQL_DELETE_USER_LOGINS);
        onCreate(sqLiteDatabase);
    }
}
