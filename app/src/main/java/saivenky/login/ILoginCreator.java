package saivenky.login;

/**
 * Created by saivenky on 1/8/17.
 */

public interface ILoginCreator {
    boolean create(UserInfo info, byte[] clientHashedPassword);
}
