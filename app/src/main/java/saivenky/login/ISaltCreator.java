package saivenky.login;

/**
 * Created by saivenky on 1/8/17.
 */

public interface ISaltCreator {
    byte[] createRandomSalt();
}
