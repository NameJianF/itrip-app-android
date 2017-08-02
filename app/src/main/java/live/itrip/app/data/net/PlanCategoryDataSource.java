package live.itrip.app.data.net;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.data.api.PlanCategoryApi;
import live.itrip.app.data.model.PlanCategoryModel;
import live.itrip.app.data.net.response.PlanCategoryResultResp;
import live.itrip.app.service.net.PlanCategoryServive;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Feng on 2017/7/12.
 */

public class PlanCategoryDataSource implements PlanCategoryApi {
    PlanCategoryServive mPlanCategoryServive;

    @Inject
    public PlanCategoryDataSource(PlanCategoryServive service) {
        this.mPlanCategoryServive = service;
    }

    @Override
    public Observable<ArrayList<PlanCategoryModel>> loadHomePageDatas() {

        Observable<PlanCategoryResultResp> list = mPlanCategoryServive.loadDatas();
        return list.map(new Func1<PlanCategoryResultResp, ArrayList<PlanCategoryModel>>() {
            @Override
            public ArrayList<PlanCategoryModel> call(PlanCategoryResultResp resp) {
                // reset datas
                ArrayList<PlanCategoryModel> list = new ArrayList<PlanCategoryModel>();
                list.add(resp.getBanner());
                list.add(resp.getNav());
                list.add(resp.getNewPlan());
                list.add(resp.getHot());
                list.add(resp.getLove());
                list.add(resp.getBlog());
                list.add(resp.getAd());

                return list;
            }
        });
    }

    @Override
    public Observable<ArrayList<PlanCategoryModel>> loadCategoryPlans(@PlanCategory int category, int flag) {
        Observable<PlanCategoryResultResp> list = mPlanCategoryServive.loadDatas();
        return list.map(new Func1<PlanCategoryResultResp, ArrayList<PlanCategoryModel>>() {
            @Override
            public ArrayList<PlanCategoryModel> call(PlanCategoryResultResp resp) {
                // reset datas
                ArrayList<PlanCategoryModel> list = new ArrayList<PlanCategoryModel>();
//                list.add(resp.getBanner());
//                list.add(resp.getNav());
//                list.add(resp.getNewPlan());
                list.add(resp.getHot());
                list.add(resp.getLove());
                list.add(resp.getBlog());
//                list.add(resp.getAd());

                return list;
            }
        });
    }
}
