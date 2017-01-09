package saivenky.login.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.security.NoSuchAlgorithmException;

import saivenky.login.ILoginCreator;
import saivenky.login.ISaltCreator;
import saivenky.login.IValidator;
import saivenky.login.utils.PasswordUtils;
import saivenky.login.SaltCreator;
import saivenky.login.UserInfo;

/**
 * Created by saivenky on 1/8/17.
 */

public class SqlLiteLoginCreator implements ILoginCreator {
    private static ILoginCreator SINGLETON;
    private final IValidator validator;
    private final ISaltCreator saltCreator;
    private final SQLiteDatabase database;

    public static ILoginCreator getInstance() throws NoSuchAlgorithmException {
        if(SINGLETON == null) {
            SINGLETON = new SqlLiteLoginCreator();
        }

        return SINGLETON;
    }

    private SqlLiteLoginCreator() throws NoSuchAlgorithmException {
        database = UserLoginDbHelper.getInstance().getWritableDatabase();
        validator = SqlLiteValidator.getInstance();
        saltCreator = new SaltCreator();
    }

    @Override
    public boolean create(UserInfo info, byte[] clientHashedPassword) {
        UserInfo userInfo = validator.hasUser(info.username);
        if(userInfo.doesExist) return false;
        addUser(info.username, info.clientSalt, clientHashedPassword);
        return true;
    }

    private void addUser(String username, byte[] clientSalt, byte[] clientHashedPassword) {

        ContentValues values = new ContentValues();
        values.put(UserLoginContract.UserLogin.COLUMN_NAME_USERNAME, username);
        values.put(UserLoginContract.UserLogin.COLUMN_NAME_CLIENT_SALT, clientSalt);

        byte[] serverSalt = saltCreator.createRandomSalt();
        byte[] serverHashedPassword = PasswordUtils.createServerHashedPassword(clientHashedPassword, serverSalt);
        values.put(UserLoginContract.UserLogin.COLUMN_NAME_SERVER_SALT, serverSalt);
        values.put(UserLoginContract.UserLogin.COLUMN_NAME_SERVER_HASHED_PASSWORD, serverHashedPassword);

        database.insert(UserLoginContract.UserLogin.TABLE_NAME, null, values);
    }
}
