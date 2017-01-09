package saivenky.login;

/**
 * Created by saivenky on 1/8/17.
 */

public class UserInfo {
    public final boolean doesExist;
    public final String username;
    public final byte[] clientSalt;

    public UserInfo(String username) {
        doesExist = false;
        this.username = username;
        clientSalt = null;
    }

    public UserInfo(String username, byte[] clientSalt) {
        doesExist = (clientSalt != null);
        this.username = username;
        this.clientSalt = clientSalt;
    }
}
