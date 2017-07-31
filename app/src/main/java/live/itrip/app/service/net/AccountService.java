package live.itrip.app.service.net;

import live.itrip.app.config.Constants;
import live.itrip.app.data.model.UserModel;
import live.itrip.app.data.net.request.CreateAuthorization;
import live.itrip.app.data.net.response.AuthorizationResp;
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
    Observable<AuthorizationResp> createAuthorization(@Body CreateAuthorization createAuthorization);

//    @GET(Constants.ApiAction.ACTION_USER)
//    rx.Observable<UserModel> getUserInfo(@Query("token") String accessToken);
}
