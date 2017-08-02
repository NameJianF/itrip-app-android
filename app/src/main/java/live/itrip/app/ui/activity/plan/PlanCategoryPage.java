package live.itrip.app.ui.activity.plan;

import live.itrip.app.R;
import live.itrip.app.ui.fragment.plan.PlanSelfGuidedFragment;

/**
 * Created by Feng on 2017/5/11.
 */

public enum PlanCategoryPage {

    /**
     * 自由行页面
     */
    PLAN_SELF_GUIDED(1, R.string.plan_category_self_guided, PlanSelfGuidedFragment.class);

    private int title;
    private Class<?> clz;
    private int value;

    PlanCategoryPage(int value, int title, Class<?> clz) {
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

    public static PlanCategoryPage getPageByValue(int val) {
        for (PlanCategoryPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }

    }
