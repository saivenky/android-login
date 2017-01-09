package saivenky.login;

/**
 * Created by saivenky on 1/8/17.
 */

public interface IHashCreator {
    byte[] hash(byte[] data);
}
