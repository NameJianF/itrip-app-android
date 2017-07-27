package live.itrip.common.util;

import android.text.TextUtils;
import android.util.Base64;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Feng on 2017/6/22.
 */

public class StringUtils {
    public static String replaceAllBlank(String str) {
        if (TextUtils.isEmpty(str)) return "";

        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(str);
        String dest = m.replaceAll("");
        return dest;
    }

    public static String trimNewLine(String str) {
        if (TextUtils.isEmpty(str)) return "";

        str = str.trim();
        Pattern p = Pattern.compile("\t|\r|\n");
        Matcher m = p.matcher(str);
        String dest = m.replaceAll("");
        return dest;
    }

    /***
     * 截取字符串
     *
     * @param start 从那里开始，0算起
     * @param num   截取多少个
     * @param str   截取的字符串
     * @return
     */
    public static String getSubString(int start, int num, String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > length) {
            start = length;
        }
        if (num < 0) {
            num = 1;
        }
        int end = start + num;
        if (end > length) {
            end = length;
        }
        return str.substring(start, end);
    }

    public static String base64Decode(String originalString) {
        if (TextUtils.isEmpty(originalString)) return "";

        return new String(Base64.decode(originalString, 0));
    }
}
