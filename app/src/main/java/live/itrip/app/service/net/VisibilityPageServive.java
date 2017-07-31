package live.itrip.app.service.net;

import live.itrip.app.data.net.response.VisibilityPageResultResp;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

/**
 * Created by Feng on 2017/7/12.
 */

public interface VisibilityPageServive {

    /**
     * 加载发现分类信息
     *
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("visi/list")
    Observable<VisibilityPageResultResp> loadDatas();
}
