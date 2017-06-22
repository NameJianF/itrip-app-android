package live.itrip.app.ui;

import live.itrip.app.R;
import live.itrip.app.ui.fragment.app.AboutFragment;
import live.itrip.app.ui.fragment.app.SettingsFragment;
import live.itrip.app.ui.fragment.app.UserInforDetailFragment;

/**
 * Created by Feng on 2017/5/11.
 */

public enum SimpleBackPage {

    /**
     * 设置页面
     */
    SETTING(15, R.string.actionbar_title_setting, SettingsFragment.class),

    USER_INFORMATION_DETAIL(28, R.string.actionbar_title_user_information,
            UserInforDetailFragment.class),

    /**
     * 关于界面
     */
    ABOUT(17, R.string.actionbar_title_about, AboutFragment.class);


    private int title;
    private Class<?> clz;
    private int value;

    private SimpleBackPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SimpleBackPage getPageByValue(int val) {
        for (SimpleBackPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }

}
