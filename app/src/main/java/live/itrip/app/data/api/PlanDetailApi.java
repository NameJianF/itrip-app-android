package live.itrip.app.data.api;

import java.util.ArrayList;

import live.itrip.app.data.model.ChildMultiItem;
import live.itrip.app.data.model.PlanCategoryModel;
import live.itrip.app.data.model.PlanDetailModel;
import rx.Completable;
import rx.Observable;

/**
 * Created by Feng on 2017/7/24.
 */

public interface PlanDetailApi {

    Observable<PlanDetailModel> getPlanDetail(Long planId);

    Observable<ArrayList<ChildMultiItem>> loadRecommendList(String category);
}
