package live.itrip.app.ui;

import live.itrip.app.ui.fragment.blog.BlogDetailFragment;

/**
 * Created by Feng on 2017/5/11.
 */

public enum DetailPage {

    /**
     * 设置页面
     */
    BLOG_DETAIL(1, BlogDetailFragment.class);


    private Class<?> clz;
    private int value;

    DetailPage(int value, Class<?> clz) {
        this.value = value;
        this.clz = clz;
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

    public static DetailPage getPageByValue(int val) {
        for (DetailPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }

}
