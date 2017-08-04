package live.itrip.app.data.net;

import android.app.Application;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import live.itrip.app.config.AppConfig;
import live.itrip.app.config.Constants;
import live.itrip.app.data.api.AccountApi;
import live.itrip.app.data.model.UserModel;
import live.itrip.app.data.net.client.AppAuthRetrofit;
import live.itrip.app.data.net.request.CreateAuthorization;
import live.itrip.app.data.net.response.AuthorizationResp;
import live.itrip.app.service.net.AccountService;
import live.itrip.common.util.SigUtil;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Feng on 2017/4/26.
 */

public class AccountDataSource implements AccountApi {

    @Inject
    AppAuthRetrofit mRetrofit;

    @Inject
    Application mContext;

    @Inject
    public AccountDataSource() {
    }

    @Override
    public Observable<UserModel> login(String username, String password) throws JSONException {
        CreateAuthorization createAuthorization = new CreateAuthorization();
        createAuthorization.setOp(Constants.ApiOp.OP_LOGIN);
        CreateAuthorization.LoginData loginData = new CreateAuthorization.LoginData();
        loginData.setEmail(username);
//        loginData.setPassword(password);
        // 采用密文
        loginData.setPassword(password);
        createAuthorization.setData(loginData);

        mRetrofit.setAuthInfo(createAuthorization);

        String json = JSON.toJSONString(createAuthorization);
        createAuthorization.setSig(SigUtil.getSig(json, AppConfig.SECRET_KEY));

        final AccountService accountService = mRetrofit.get().create(AccountService.class);

        Observable<AuthorizationResp> respObservable = accountService.createAuthorization(createAuthorization);
        return respObservable.map(new Func1<AuthorizationResp, UserModel>() {
            @Override
            public UserModel call(AuthorizationResp resp) {
                return resp.getAuthor();
            }
        });

    }
}