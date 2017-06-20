package live.itrip.app.data.net;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Inject;

import live.itrip.app.config.AppConfig;
import live.itrip.app.data.PreferenceData;
import live.itrip.app.data.api.AccountApi;
import live.itrip.app.data.model.User;
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
    public Observable<User> login(String username, String password) {
        CreateAuthorization createAuthorization = new CreateAuthorization();
        createAuthorization.setOp("Sso.login");
        createAuthorization.setEmail(username);
        createAuthorization.setPassword(password);
        mRetrofit.setAuthInfo(createAuthorization);
        String json = new Gson().toJson(createAuthorization).trim();
        createAuthorization.setSig(SigUtil.getSig(json, AppConfig.SECRET_KEY));

        final AccountService accountService = mRetrofit.get().create(AccountService.class);

        return accountService.createAuthorization(createAuthorization)
                .flatMap(new Func1<AuthorizationResp, Observable<User>>() {
                    @Override
                    public Observable<User> call(AuthorizationResp authorizationResp) {

                        String token = authorizationResp.getToken();

                        // save token
                        PreferenceData.Account.saveLoginToken(mContext, token);

                        return accountService.getUserInfo(authorizationResp.getToken());
                    }
                });
    }
}