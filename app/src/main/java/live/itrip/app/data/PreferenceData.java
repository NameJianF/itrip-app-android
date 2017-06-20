package live.itrip.app.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import live.itrip.app.App;
import live.itrip.app.data.model.User;
import live.itrip.app.ui.activity.account.LoginActivity;

/**
 * Created by Feng on 2017/4/25.
 */

public class PreferenceData {
    private static final String KEY_IS_FIRST_RUNNING = "isFirstRunning";
    private static final String KEY_LOGIN_TOKEN = "login_token";
    private static final String KEY_LOGON_USER = "logon_user";


    private static SharedPreferences getPreference(Context context) {
        return context.getApplicationContext()
                .getSharedPreferences("live.itrip.app.app_preference.xml", Context.MODE_PRIVATE);
    }

    public static void setAlreadyRun(Context context) {
        getPreference(context).edit().putBoolean(KEY_IS_FIRST_RUNNING, false).apply();
    }

    public static boolean isFirstRunning(Context context) {
        return getPreference(context).getBoolean(KEY_IS_FIRST_RUNNING, true);
    }

    /**
     * Account datas
     */
    public static class Account {
        public static void saveLoginToken(Context context, String loginToken) {
            getPreference(context).edit().putString(KEY_LOGIN_TOKEN, loginToken).apply();
        }

        public static String getLogonToken(Context context) {
            return getPreference(context).getString(KEY_LOGIN_TOKEN, "");
        }

        public static void saveLogonUser(Context context, User user) {
            String userJson = new Gson().toJson(user);
            getPreference(context).edit().putString(KEY_LOGON_USER, userJson).apply();
        }

        public static void removeLogonUser(Context context) {
            getPreference(context).edit().remove(KEY_LOGON_USER).apply();
        }

        public static User getLogonUser(Context context) {
            User user = null;
            String userJson = getPreference(context).getString(KEY_LOGON_USER, "");
            if (!TextUtils.isEmpty(userJson)) {
                user = new Gson().fromJson(userJson, User.class);
            }
            return user;
        }

        public static boolean isLogon(Context context) {
            return !TextUtils.isEmpty(getLogonToken(context)) && getLogonUser(context) != null;
        }

        public static boolean checkLogon(Context context) {
            if (!isLogon(context)) {
                LoginActivity.launch(context);
                return false;
            }

            return true;
        }

        public static boolean isSelf(Context context, String username) {
            User user = getLogonUser(context);
            return user != null
                    && !TextUtils.isEmpty(username)
                    && username.equals(user.getLogin());
        }

        public static void clearUserCache() {

        }

        public static Integer getUserId() {
            User user = getLogonUser(App.getContext());
            if (user != null) {
                return user.getId();
            }
            return -1;
        }
    }
}
