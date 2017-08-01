package live.itrip.app.ui;

import live.itrip.app.ui.fragment.blog.BlogDetailFragment;
import live.itrip.app.ui.fragment.plan.PlanDetailFragment;

/**
 * Created by Feng on 2017/5/11.
 */

public enum DetailPage {

    /**
     * 博客详情页面
     */
    DETAIL_BLOG(1, BlogDetailFragment.class),

    /**
     * 行程详情页面
     */
    DETAIL_PLAN(2, PlanDetailFragment.class);

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
