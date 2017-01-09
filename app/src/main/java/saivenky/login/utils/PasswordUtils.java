package saivenky.login.utils;

import saivenky.login.IHashCreator;

/**
 * Created by saivenky on 1/8/17.
 */

public final class PasswordUtils {
    private static IHashCreator hashCreator;
    public static void initialize(IHashCreator hashCreator) {
        PasswordUtils.hashCreator = hashCreator;
    }

    public static byte[] createServerHashedPassword(byte[] clientHashedPassword, byte[] serverSalt) {
        byte[] clientHashedPasswordWithServerSalt = ByteUtils.concatBytes(clientHashedPassword, serverSalt);
        return hashCreator.hash(clientHashedPasswordWithServerSalt);
    }

    public static byte[] createClientHashedPassword(String password, byte[] clientSalt) {
        byte[] passwordBytes = ByteUtils.stringToBytes(password);
        byte[] passwordWithClientSalt = ByteUtils.concatBytes(passwordBytes, clientSalt);
        return hashCreator.hash(passwordWithClientSalt);
    }
}
