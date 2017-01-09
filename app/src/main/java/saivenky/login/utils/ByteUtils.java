package saivenky.login.utils;

import java.nio.charset.Charset;

/**
 * Created by saivenky on 1/8/17.
 */

public class ByteUtils {
    public static byte[] stringToBytes(String text) {
        return text.getBytes(Charset.defaultCharset());
    }

    public static byte[] concatBytes(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static boolean isEqual(byte[] a, byte[] b) {
        if(a == null || b == null) return false;
        if(a.length != b.length) return false;
        for(int i = 0; i < a.length; i++) {
            if(a[i] != b[i]) return false;
        }

        return true;
    }

}
