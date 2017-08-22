package live.itrip.app.data.net;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;

import javax.inject.Inject;

import live.itrip.app.config.AppConfig;
import live.itrip.app.config.Constants;
import live.itrip.app.data.api.SettingApi;
import live.itrip.app.data.model.UpdateModel;
import live.itrip.app.data.net.client.AppAuthRetrofit;
import live.itrip.app.data.net.request.LogoutParams;
import live.itrip.app.data.net.response.ResultResp;
import live.itrip.app.data.net.response.SettingResultResp;
import live.itrip.app.service.net.AccountService;
import live.itrip.app.service.net.SettingService;
import live.itrip.common.util.SigUtil;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Feng on 2017/6/28.
 */

public class SettingDataSource implements SettingApi {
    SettingService mSettingService;

    @Inject
    AppAuthRetrofit mAppAuthRetrofit;

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


    @Override
    public Observable<ResultResp> logout(String username, String token) throws JSONException {
        LogoutParams request = new LogoutParams();
        request.setOp(Constants.ApiOp.OP_LOGOUT);
//        request.setUid();
        request.setEmail(username);
        request.setSid(token);

        String json = JSON.toJSONString(request);
        request.setSig(SigUtil.getSig(json, AppConfig.SECRET_KEY));

        mAppAuthRetrofit.setPostJsonString(JSON.toJSONString(request));

        final AccountService accountService = mAppAuthRetrofit.get().create(AccountService.class);

        Observable<ResultResp> respObservable = accountService.logout();
        return respObservable.map(new Func1<ResultResp, ResultResp>() {
            @Override
            public ResultResp call(ResultResp resp) {
                return resp;
            }
        });
    }
}
