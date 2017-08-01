package live.itrip.app.data.api;

import live.itrip.app.data.model.PlanDetailModel;
import rx.Observable;

/**
 * Created by Feng on 2017/7/24.
 */

public interface PlanDetailApi {
    Observable<PlanDetailModel> getPlanDetail(Long planId);
}
