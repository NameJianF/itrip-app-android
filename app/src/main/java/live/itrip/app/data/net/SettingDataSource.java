package live.itrip.app.data.net;

import javax.inject.Inject;

import live.itrip.app.data.api.SettingApi;
import live.itrip.app.data.model.UpdateModel;
import live.itrip.app.data.net.response.SettingResultResp;
import live.itrip.app.service.net.SettingService;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Feng on 2017/6/28.
 */

public class SettingDataSource implements SettingApi {
    SettingService mSettingService;

    @Inject
    public SettingDataSource(SettingService service) {
        this.mSettingService = service;
    }

    @Override
    public Observable<UpdateModel> checkAppVersion() {
        return mSettingService.checkAppVersion()
                .map(new Func1<SettingResultResp, UpdateModel>() {

                    @Override
                    public UpdateModel call(SettingResultResp resp) {
                        UpdateModel model = new UpdateModel();
                        model.setVersionCode(resp.getVersionCode());
                        model.setVersionName(resp.getVersionName());
                        model.setDesc(resp.getDesc());
                        model.setDownloadUrl(resp.getDownloadUrl());
                        model.setPublishDate(resp.getPublishDate());
                        return model;
                    }
                });
    }
}
