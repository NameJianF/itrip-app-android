package live.itrip.app.data.net;

import android.app.Application;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;

import javax.inject.Inject;

import live.itrip.app.config.AppConfig;
import live.itrip.app.config.Constants;
import live.itrip.app.data.api.AccountApi;
import live.itrip.app.data.model.UserExpandModel;
import live.itrip.app.data.model.UserModel;
import live.itrip.app.data.net.client.AppAuthRetrofit;
import live.itrip.app.data.net.request.CreateAuthorization;
import live.itrip.app.data.net.request.UserExpandInfoParams;
import live.itrip.app.data.net.response.AuthorizationResp;
import live.itrip.app.data.net.response.UserExpandInfoResp;
import live.itrip.app.service.net.AccountService;
import live.itrip.common.util.Md5Utils;
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
        loginData.setCiphertext(Constants.PASSWORD_CIPHERTEXT); // 密码采用密文
        loginData.setPassword(Md5Utils.getStringMD5(password));
        createAuthorization.setData(loginData);

        String json = JSON.toJSONString(createAuthorization);
        createAuthorization.setSig(SigUtil.getSig(json, AppConfig.SECRET_KEY));

        mRetrofit.setPostJsonString(JSON.toJSONString(createAuthorization));

        final AccountService accountService = mRetrofit.get().create(AccountService.class);

        Observable<AuthorizationResp> respObservable = accountService.createAuthorization();
        return respObservable.map(new Func1<AuthorizationResp, UserModel>() {
            @Override
            public UserModel call(AuthorizationResp resp) {
                return resp.getAuthor();
            }
        });

    }

    @Override
    public Observable<UserExpandModel> getUserExpandInfo(String username, String token) throws JSONException {
        UserExpandInfoParams request = new UserExpandInfoParams();
        request.setOp(Constants.ApiOp.OP_USER_INFO);
        request.setEmail(username);
        request.setSid(token);

        String json = JSON.toJSONString(request);
        request.setSig(SigUtil.getSig(json, AppConfig.SECRET_KEY));

        mRetrofit.setPostJsonString(JSON.toJSONString(request));

        final AccountService accountService = mRetrofit.get().create(AccountService.class);

        Observable<UserExpandInfoResp> respObservable = accountService.getUserExpandInfo();
        return respObservable.map(new Func1<UserExpandInfoResp, UserExpandModel>() {
            @Override
            public UserExpandModel call(UserExpandInfoResp resp) {
                return resp.getUserExpandModel();
            }
        });
    }

}