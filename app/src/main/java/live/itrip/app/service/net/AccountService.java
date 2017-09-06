package live.itrip.app.service.net;

import live.itrip.app.config.Constants;
import live.itrip.app.data.model.UserExpandModel;
import live.itrip.app.data.model.UserModel;
import live.itrip.app.data.net.request.CreateAuthorization;
import live.itrip.app.data.net.request.LogoutParams;
import live.itrip.app.data.net.response.AuthorizationResp;
import live.itrip.app.data.net.response.ResultResp;
import live.itrip.app.data.net.response.UserExpandInfoResp;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Feng on 2017/4/26.
 */
public interface AccountService {

    @POST(Constants.ApiAction.ACTION_SSO)
    Observable<AuthorizationResp> createAuthorization();

    @POST(Constants.ApiAction.ACTION_SSO)
    Observable<ResultResp> logout();

    @POST(Constants.ApiAction.ACTION_USER)
    Observable<UserExpandInfoResp> getUserExpandInfo();

    @POST(Constants.ApiAction.ACTION_USER)
    Observable<ResultResp> validateSmsCode();

    @POST(Constants.ApiAction.ACTION_USER)
    Observable<ResultResp> sendSmsCode();

    @POST(Constants.ApiAction.ACTION_USER)
    Observable<ResultResp> resetPwd();

    @POST(Constants.ApiAction.ACTION_USER)
    Observable<AuthorizationResp> register();
}
