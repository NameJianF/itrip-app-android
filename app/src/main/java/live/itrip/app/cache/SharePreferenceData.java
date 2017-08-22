package live.itrip.app.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;

import com.google.gson.Gson;

import live.itrip.app.App;
import live.itrip.app.data.model.UserModel;
import live.itrip.app.ui.activity.account.LoginActivity;

/**
 * Created by Feng on 2017/4/25.
 */

public class SharePreferenceData {
    private static final String KEY_IS_FIRST_RUNNING = "isFirstRunning";
    private static final String KEY_LOGIN_TOKEN = "login_token";
    private static final String KEY_LOGON_USER = "logon_user";


    private static final String KEY_SOFT_KEYBOARD_HEIGHT = "softKeyboardHeight";

    private static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(DataCacheManager.SHAREDPREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void setAlreadyRun(Context context) {
        getPreference(context).edit().putBoolean(KEY_IS_FIRST_RUNNING, false).apply();
    }

    public static boolean isFirstRunning(Context context) {
        return getPreference(context).getBoolean(KEY_IS_FIRST_RUNNING, true);
    }


    public static void set(String key, int value) {
        SharedPreferences.Editor editor = getPreference(App.getContext()).edit();
        editor.putInt(key, value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static void set(String key, boolean value) {
        SharedPreferences.Editor editor = getPreference(App.getContext()).edit();
        editor.putBoolean(key, value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static void set(String key, String value) {
        SharedPreferences.Editor editor = getPreference(App.getContext()).edit();
        editor.putString(key, value);
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }

    public static boolean get(String key, boolean defValue) {
        return getPreference(App.getContext()).getBoolean(key, defValue);
    }

    public static String get(String key, String defValue) {
        return getPreference(App.getContext()).getString(key, defValue);
    }

    public static int get(String key, int defValue) {
        return getPreference(App.getContext()).getInt(key, defValue);
    }

    public static long get(String key, long defValue) {
        return getPreference(App.getContext()).getLong(key, defValue);
    }

    public static float get(String key, float defValue) {
        return getPreference(App.getContext()).getFloat(key, defValue);
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

        public static void saveLogonUser(Context context, UserModel user) {
            String userJson = new Gson().toJson(user);
            getPreference(context).edit().putString(KEY_LOGON_USER, userJson).apply();
        }

        public static void removeLogonUser(Context context) {
            // remove user
            getPreference(context).edit().remove(KEY_LOGON_USER).apply();
            // remove token
            getPreference(context).edit().remove(KEY_LOGIN_TOKEN).apply();
        }

        public static void removeLogonUser() {
            // remove user
            getPreference(App.getContext()).edit().remove(KEY_LOGON_USER).apply();
            // remove token
            getPreference(App.getContext()).edit().remove(KEY_LOGIN_TOKEN).apply();
        }


        public static UserModel getLogonUser(Context context) {
            UserModel user = null;
            String userJson = getPreference(context).getString(KEY_LOGON_USER, "");
            if (!TextUtils.isEmpty(userJson)) {
                user = new Gson().fromJson(userJson, UserModel.class);
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

//        public static boolean isSelf(Context context, String username) {
//            UserModel user = getLogonUser(context);
//            return user != null
//                    && !TextUtils.isEmpty(username)
//                    && username.equals(user.getLogin());
//        }

        public static void clearUserCache() {

        }

        public static Long getUserId() {
            UserModel user = getLogonUser(App.getContext());
            if (user != null) {
                return user.getId();
            }
            return 0L;
        }
    }


    public static class SoftKeyboard {

        public static int getSoftKeyboardHeight(Context context) {
            SharedPreferences sp = getPreference(context);
            return sp.getInt(KEY_SOFT_KEYBOARD_HEIGHT, 0);
        }

        public static void updateSoftKeyboardHeight(Context context, int height) {
            SharedPreferences sp = getPreference(context);
            SharedPreferences.Editor editor = sp.edit().putInt(KEY_SOFT_KEYBOARD_HEIGHT, height);
            SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
        }
    }

}
