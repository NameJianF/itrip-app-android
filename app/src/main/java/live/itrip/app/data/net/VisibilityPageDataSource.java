package live.itrip.app.data.net;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.data.api.VisibilityPageApi;
import live.itrip.app.data.model.VisibilityPageModel;
import live.itrip.app.data.net.response.VisibilityPageResultResp;
import live.itrip.app.service.net.VisibilityPageServive;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Feng on 2017/7/12.
 */

public class VisibilityPageDataSource implements VisibilityPageApi {
    VisibilityPageServive mVisibilityPageServive;

    @Inject
    public VisibilityPageDataSource(VisibilityPageServive service) {
        this.mVisibilityPageServive = service;
    }

    @Override
    public Observable<ArrayList<VisibilityPageModel>> loadDatas() {

        Observable<VisibilityPageResultResp> list = mVisibilityPageServive.loadDatas();
        return list.map(new Func1<VisibilityPageResultResp, ArrayList<VisibilityPageModel>>() {
            @Override
            public ArrayList<VisibilityPageModel> call(VisibilityPageResultResp resp) {
                ArrayList<VisibilityPageModel> list = new ArrayList<VisibilityPageModel>();
                list.add(resp.getNav());
                list.add(resp.getHot());
                list.add(resp.getBlog());
                list.add(resp.getCategory());
                list.add(resp.getAd());

                return list;
            }
        });
    }
}
