package live.itrip.app.config;

/**
 * Created by Feng on 2017/6/26.
 */

public class Constants {
    public static final int PAGE_SIZE = 30;

    public static class ApiAction {
        public static final String ACTION_SSO = "sso.action";
        public static final String ACTION_USER = "user.action";
    }

    public static class ApiOp {
        public static final String OP_LOGIN = "Sso.login";
    }

    public enum PlanType {
        /**
         * 自由行
         */
        SelfGuided,
        /**
         * 跟团游
         */
        GroupTravel,
        /**
         * 主题旅游
         */
        ThemeTravel,
        /**
         * 乡村民宿
         */
        CountryInn,
        /**
         * 旅行服务
         */
        TravelService;

    }
}
