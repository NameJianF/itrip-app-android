package live.itrip.app.service.net;

import live.itrip.app.data.net.response.HomePageResultResp;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Feng on 2017/7/12.
 */

public interface HomePageServive {

    /**
     * 加载首页分类信息
     *
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("home/list")
    Observable<HomePageResultResp> loadDatas();
}
