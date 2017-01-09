package saivenky.login;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by saivenky on 1/8/17.
 */

public class SaltCreator implements ISaltCreator{
    private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
    private static final int SALT_LENGTH_BYTES = 20;

    private SecureRandom random;

    public SaltCreator() throws NoSuchAlgorithmException {
        random = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
    }

    @Override
    public byte[] createRandomSalt() {
        return random.generateSeed(SALT_LENGTH_BYTES);
    }
}
