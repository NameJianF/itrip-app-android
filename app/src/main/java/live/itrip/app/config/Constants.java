package live.itrip.app.config;

/**
 * Created by Feng on 2017/6/26.
 */

public class Constants {
    public static final int PAGE_SIZE = 30;

    /**
     * 密码是密文
     */
    public static final String PASSWORD_CIPHERTEXT = "1";
    /**
     * 密码不是密文
     */
    public static final String PASSWORD_NOT_CIPHERTEXT = "0";



    public static class ApiAction {
        public static final String ACTION_SSO = "sso.action";
        public static final String ACTION_USER = "user.action";
    }

    public static class ApiOp {
        public static final String OP_LOGIN = "Sso.login";
        /**
         * 退出
         */
        public static final String OP_LOGOUT = "Sso.logout";

        /**
         * 获取用户信息
         */
        public static final String OP_USER_INFO = "User.info";
    }

}
