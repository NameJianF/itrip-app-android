package live.itrip.app.util;

import java.util.regex.Pattern;

/**
 * Created by Feng on 2017/9/6.
 */

public class ParserUtils {
    /**
     * 判断手机输入合法
     *
     * @param phoneNumber 手机号码
     * @return true|false
     */
    public static boolean machPhoneNum(CharSequence phoneNumber) {
        String regex = "^[1][34578][0-9]\\d{8}$";
        return Pattern.matches(regex, phoneNumber);
    }
}
