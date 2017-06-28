package live.itrip.app.service.net;


import live.itrip.app.data.net.response.SettingResultResp;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Feng on 2017/6/28.
 */

public interface SettingService {

    @GET("/ver/android")
    Observable<SettingResultResp> checkAppVersion();

}
