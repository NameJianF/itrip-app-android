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
    /**
     * 用于找回密码
     */
    public static final String RETRIEVE_PWD_URL = "https://www.tourin.cn/user/resetPwd.action";


    public static class SmsType {
        public static final int RESET_PWD_INTENT = 1;
        public static final int REGISTER_INTENT = 2;
    }


    public static class ApiAction {
        public static final String ACTION_SSO = "sso.action";
        public static final String ACTION_USER = "user.action";
        public static final String ACTION_FEEDBACK = "feedback.action";
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


        /**
         * 修改密码
         */
        public static final String OP_USER_MODIFY_PASSWORD = "User.updatePassword";

        /**
         * 注册
         */
        public static final String OP_USER_REGISTER = "User.register";

        /**
         * 验证短信验证码
         */
        public static final String OP_USER_VALIDATE_SMS_CODE = "User.validateSmsCode";

        /**
         * 发送短信验证码
         */
        public static final String OP_USER_SEND_SMS_CODE = "User.sendSmsCode";

        /**
         * 提交反馈信息
         */
        public static final String OP_FEEDBACK_SUBMIT = "Feedback.submit";
    }

}
