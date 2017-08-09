package live.itrip.app.data.net;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.data.api.PlanDetailApi;
import live.itrip.app.data.model.ChildMultiItem;
import live.itrip.app.data.model.PlanCategoryModel;
import live.itrip.app.data.model.PlanDetailModel;
import live.itrip.app.data.net.response.PlanCategoryResultResp;
import live.itrip.app.data.net.response.PlanDetailResultResp;
import live.itrip.app.data.net.response.RecommendResultResp;
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

    @Override
    public Observable<ArrayList<ChildMultiItem>> loadRecommendList(String category) {
        return this.mPlanDetailService.loadRecommendList(category)
                .map(new Func1<RecommendResultResp, ArrayList<ChildMultiItem>>() {
                    @Override
                    public ArrayList<ChildMultiItem> call(RecommendResultResp resp) {
                        return resp.getItemList();
                    }
                });
    }
}
