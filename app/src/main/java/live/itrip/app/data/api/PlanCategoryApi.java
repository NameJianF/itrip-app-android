package live.itrip.app.data.api;

import android.support.annotation.StringDef;

import java.util.ArrayList;

import live.itrip.app.data.model.PlanCategoryModel;
import rx.Observable;

/**
 * Created by Feng on 2017/7/12.
 */

public interface PlanCategoryApi {
    /**
     * 自由行
     */
    String FLAG_SELF_GUIDED = "1";
    /**
     * 跟团游
     */
    String FLAG_GROUP_TRAVEL = "2";
    /**
     * 主题旅游
     */
    String FLAG_THEME_TRAVEL = "3";
    /**
     * 乡村民宿
     */
    String FLAG_COUNTRY_INN = "4";
    /**
     * 旅行服务
     */
    String FLAG_TRAVEL_SERVICE = "5";

    @StringDef({
            FLAG_SELF_GUIDED,
            FLAG_GROUP_TRAVEL,
            FLAG_THEME_TRAVEL,
            FLAG_COUNTRY_INN,
            FLAG_TRAVEL_SERVICE
    })
    @interface PlanCategory {

    }


    Observable<ArrayList<PlanCategoryModel>> loadHomePageDatas();

    /**
     * @param category 类别
     * @param flag     标记，如城市
     * @return
     */
    Observable<ArrayList<PlanCategoryModel>> loadCategoryPlans(@PlanCategory String category, int flag);
}
