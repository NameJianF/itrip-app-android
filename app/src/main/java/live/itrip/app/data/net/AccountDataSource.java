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
import live.itrip.app.data.net.request.RegisterParams;
import live.itrip.app.data.net.request.ResetPasswordParams;
import live.itrip.app.data.net.request.SendSmsCodeParams;
import live.itrip.app.data.net.request.UserExpandInfoParams;
import live.itrip.app.data.net.request.ValidateSmsCodeParams;
import live.itrip.app.data.net.response.AuthorizationResp;
import live.itrip.app.data.net.response.ResultResp;
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

    @Override
    public Observable<UserModel> openLogin(int catalog, String openInfo) throws JSONException {
        // test
        return this.login("fjf789@126.com", "123456");

//        LogoutParams request = new LogoutParams();
//        request.setOp(Constants.ApiOp.OP_LOGOUT);
//        request.setEmail(username);
//        request.setSid(token);
//
//        String json = JSON.toJSONString(request);
//        request.setSig(SigUtil.getSig(json, AppConfig.SECRET_KEY));
//
//        mRetrofit.setPostJsonString(JSON.toJSONString(request));
//
//        final AccountService accountService = mAppAuthRetrofit.get().create(AccountService.class);
//
//        Observable<ResultResp> respObservable = accountService.logout();
//        return respObservable.map(new Func1<ResultResp, ResultResp>() {
//            @Override
//            public ResultResp call(ResultResp resp) {
//                return resp;
//            }
//        });
    }

    @Override
    public Observable<ResultResp> validateSmsCode(String phoneNumber, String smsCode) throws JSONException {
        ValidateSmsCodeParams request = new ValidateSmsCodeParams();
        request.setOp(Constants.ApiOp.OP_USER_VALIDATE_SMS_CODE);
        request.setPhoneNumber(phoneNumber);
        request.setSmsCode(smsCode);

        String json = JSON.toJSONString(request);
        request.setSig(SigUtil.getSig(json, AppConfig.SECRET_KEY));

        mRetrofit.setPostJsonString(JSON.toJSONString(request));

        final AccountService accountService = mRetrofit.get().create(AccountService.class);

        Observable<ResultResp> respObservable = accountService.validateSmsCode();
        return respObservable.map(new Func1<ResultResp, ResultResp>() {
            @Override
            public ResultResp call(ResultResp resp) {
                return resp;
            }
        });
    }

    @Override
    public Observable<ResultResp> sendSmsCode(String phoneNumber, int smsType) throws JSONException {
        SendSmsCodeParams request = new SendSmsCodeParams();
        request.setOp(Constants.ApiOp.OP_USER_SEND_SMS_CODE);
        request.setPhoneNumber(phoneNumber);
        request.setSmsType(smsType);

        String json = JSON.toJSONString(request);
        request.setSig(SigUtil.getSig(json, AppConfig.SECRET_KEY));

        mRetrofit.setPostJsonString(JSON.toJSONString(request));

        final AccountService accountService = mRetrofit.get().create(AccountService.class);

        Observable<ResultResp> respObservable = accountService.sendSmsCode();
        return respObservable.map(new Func1<ResultResp, ResultResp>() {
            @Override
            public ResultResp call(ResultResp resp) {
                return resp;
            }
        });
    }

    @Override
    public Observable<ResultResp> resetPwd(String newPwd, String phoneToken) {
        ResetPasswordParams request = new ResetPasswordParams();
        request.setOp(Constants.ApiOp.OP_USER_MODIFY_PASSWORD);
        request.setNewPwd(newPwd);
        request.setPhoneToken(phoneToken);

        String json = JSON.toJSONString(request);
        request.setSig(SigUtil.getSig(json, AppConfig.SECRET_KEY));

        mRetrofit.setPostJsonString(JSON.toJSONString(request));

        final AccountService accountService = mRetrofit.get().create(AccountService.class);

        Observable<ResultResp> respObservable = accountService.resetPwd();
        return respObservable.map(new Func1<ResultResp, ResultResp>() {
            @Override
            public ResultResp call(ResultResp resp) {
                return resp;
            }
        });
    }

    @Override
    public Observable<UserModel> register(String username, String pwd, int sex, String phoneToken) {
        RegisterParams request = new RegisterParams();
        request.setOp(Constants.ApiOp.OP_USER_REGISTER);
        request.setUserName(username);
        request.setPassword(pwd);
        request.setSex(sex);
        request.setPhoneToken(phoneToken);

        String json = JSON.toJSONString(request);
        request.setSig(SigUtil.getSig(json, AppConfig.SECRET_KEY));

        mRetrofit.setPostJsonString(JSON.toJSONString(request));

        final AccountService accountService = mRetrofit.get().create(AccountService.class);

        Observable<AuthorizationResp> respObservable = accountService.register();
        return respObservable.map(new Func1<AuthorizationResp, UserModel>() {
            @Override
            public UserModel call(AuthorizationResp resp) {
                return resp.getAuthor();
            }
        });
    }

}