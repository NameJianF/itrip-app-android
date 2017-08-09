package live.itrip.app.service.net;

import live.itrip.app.data.net.response.PlanCategoryResultResp;
import live.itrip.app.data.net.response.PlanDetailResultResp;
import live.itrip.app.data.net.response.RecommendResultResp;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Feng on 2017/7/24.
 */

public interface PlanDetailService {
    /**
     * get plan detail
     *
     * @param planId
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("plan/{id}")
    Observable<PlanDetailResultResp> getPlanDetail(@Path("id") Long planId);

    @Headers("Cache-Control: public, max-age=3600")
    @GET("plan/recommends/{category}")
    Observable<RecommendResultResp> loadRecommendList(@Path("category") String category);
}
