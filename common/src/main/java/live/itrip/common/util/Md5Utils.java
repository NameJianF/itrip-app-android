package live.itrip.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Feng on 2017/8/21.
 */

public class Md5Utils {
    /**
     * 获取String的md5值，异常时返回null
     *
     * @param string
     * @return
     */
    public static String getStringMD5(String string) {
        try {
            byte[] hash;
            try {
                hash = MessageDigest.getInstance("MD5").digest(
                        string.getBytes("UTF-8"));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Huh, MD5 should be supported?", e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Huh, UTF-8 should be supported?", e);
            }

            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10)
                    hex.append("0");
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (Exception e) {
            // Logging.error(e.getMessage());
            return null;
        }
    }
}