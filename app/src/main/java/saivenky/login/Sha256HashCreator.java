package saivenky.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by saivenky on 1/8/17.
 */

public class Sha256HashCreator implements IHashCreator {
    private static final String DIGEST_ALGORITHM = "SHA-256";
    private static IHashCreator SINGLETON;

    public static IHashCreator getInstance() throws NoSuchAlgorithmException{
        if(SINGLETON == null) {
            SINGLETON = new Sha256HashCreator();
        }

        return SINGLETON;
    }

    private final MessageDigest digest;

    private Sha256HashCreator() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance(DIGEST_ALGORITHM);
    }

    @Override
    public byte[] hash(byte[] data) {
        return digest.digest(data);
    }
}
