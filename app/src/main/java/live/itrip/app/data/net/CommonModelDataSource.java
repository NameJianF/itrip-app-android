package live.itrip.app.data.net;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.data.api.CommonModelApi;
import live.itrip.app.data.model.CommonModel;
import live.itrip.app.service.net.CommonModelService;
import rx.Observable;

/**
 * Created by Feng on 2017/6/22.
 */

public class CommonModelDataSource implements CommonModelApi {

    private CommonModelService mCommonModelService;

    @Inject
    public CommonModelDataSource(CommonModelService service) {
        this.mCommonModelService = service;
    }

    @Override
    public Observable<ArrayList<CommonModel>> getDubbles(Integer userid) {
        return this.mCommonModelService.getDubbles(userid);
    }
}
