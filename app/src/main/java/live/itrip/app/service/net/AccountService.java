package live.itrip.app.service.net;

import live.itrip.app.data.model.User;
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

    @POST("/sso.action")
    Observable<AuthorizationResp> createAuthorization(@Body CreateAuthorization createAuthorization);

    @GET("/user.action")
    rx.Observable<User> getUserInfo(@Query("access_token") String accessToken);
}
