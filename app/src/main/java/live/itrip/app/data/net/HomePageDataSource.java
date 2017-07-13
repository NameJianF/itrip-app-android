package live.itrip.app.data.net;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.config.Constants;
import live.itrip.app.data.api.HomePageApi;
import live.itrip.app.data.model.HomePageModel;
import live.itrip.app.data.net.response.HomePageResultResp;
import live.itrip.app.service.net.HomePageServive;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Feng on 2017/7/12.
 */

public class HomePageDataSource implements HomePageApi {
    HomePageServive mHomePageServive;

    @Inject
    public HomePageDataSource(HomePageServive service) {
        this.mHomePageServive = service;
    }

    @Override
    public Observable<ArrayList<HomePageModel>> loadDatas() {

        Observable<HomePageResultResp> list = mHomePageServive.loadDatas();
        return list.map(new Func1<HomePageResultResp, ArrayList<HomePageModel>>() {
            @Override
            public ArrayList<HomePageModel> call(HomePageResultResp resp) {
                return resp.getDataList();
            }
        });
    }
}
