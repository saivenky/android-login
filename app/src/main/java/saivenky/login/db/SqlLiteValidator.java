package saivenky.login.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.security.NoSuchAlgorithmException;

import saivenky.login.utils.ByteUtils;
import saivenky.login.IValidator;
import saivenky.login.LoginInfo;
import saivenky.login.utils.PasswordUtils;
import saivenky.login.UserInfo;

/**
 * Created by saivenky on 1/7/17.
 */

public class SqlLiteValidator implements IValidator {
    private static IValidator SINGLETON;
    private final SQLiteDatabase database;

    public static IValidator getInstance() throws NoSuchAlgorithmException {
        if(SINGLETON == null) {
            SINGLETON = new SqlLiteValidator();
        }

        return SINGLETON;
    }

    private SqlLiteValidator() throws NoSuchAlgorithmException {
        database = UserLoginDbHelper.getInstance().getReadableDatabase();
    }

    @Override
    public UserInfo hasUser(String username) {
        Cursor cursor = getByUsername(username);
        int results = cursor.getCount();
        if(results != 1) return createNoUser(username);

        cursor.moveToFirst();
        byte[] clientSalt = getClientSalt(cursor);
        cursor.close();

        return new UserInfo(username, clientSalt);
    }

    @Override
    public boolean validLogin(LoginInfo loginInfo) {
        Cursor cursor = getByUsername(loginInfo.username);

        int results = cursor.getCount();
        if(results != 1) return false;

        cursor.moveToFirst();
        byte[] serverSalt = getServerSalt(cursor);
        byte[] actualServerHashedPassword = getServerHashedPassword(cursor);
        cursor.close();

        byte[] serverHashedPassword = PasswordUtils.createServerHashedPassword(loginInfo.clientHashedPassword, serverSalt);

        return ByteUtils.isEqual(serverHashedPassword, actualServerHashedPassword);
    }

    private static UserInfo createNoUser(String username) {
        return new UserInfo(username);
    }

    private static final String[] QUERY_PROJECTION = {
            UserLoginContract.UserLogin.COLUMN_NAME_CLIENT_SALT,
            UserLoginContract.UserLogin.COLUMN_NAME_SERVER_SALT,
            UserLoginContract.UserLogin.COLUMN_NAME_SERVER_HASHED_PASSWORD
    };

    private byte[] getClientSalt(Cursor cursor) {
        int CLIENT_SALT_INDEX = 0;
        return cursor.getBlob(CLIENT_SALT_INDEX);
    }

    private byte[] getServerSalt(Cursor cursor) {
        int SERVER_SALT_INDEX = 1;
        return cursor.getBlob(SERVER_SALT_INDEX);
    }

    private byte[] getServerHashedPassword(Cursor cursor) {
        int SERVER_HASHED_PASSWORD_INDEX = 2;
        return cursor.getBlob(SERVER_HASHED_PASSWORD_INDEX);
    }

    private Cursor getByUsername(String username) {
        String selection = UserLoginContract.UserLogin.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = { username };

        return database.query(
                UserLoginContract.UserLogin.TABLE_NAME,                     // The table to query
                QUERY_PROJECTION,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );
    }
}
