package live.itrip.app.data.net;

import javax.inject.Inject;

import live.itrip.app.data.api.PlanDetailApi;
import live.itrip.app.data.model.BlogDetailModel;
import live.itrip.app.data.model.PlanDetailModel;
import live.itrip.app.data.net.response.BlogDetailResultResp;
import live.itrip.app.data.net.response.PlanDetailResultResp;
import live.itrip.app.service.net.PlanDetailService;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Feng on 2017/7/24.
 */

public class PlanDetailDataSource implements PlanDetailApi {

    PlanDetailService mPlanDetailService;

    @Inject
    public PlanDetailDataSource(PlanDetailService service) {
        this.mPlanDetailService = service;
    }

    @Override
    public Observable<PlanDetailModel> getPlanDetail(Long planId) {
        Observable<PlanDetailResultResp> blogDetail = mPlanDetailService.getPlanDetail(planId);

        return blogDetail.map(new Func1<PlanDetailResultResp, PlanDetailModel>() {
            @Override
            public PlanDetailModel call(PlanDetailResultResp resp) {
                return resp.getPlanDetailModel();
            }
        });
    }
}
