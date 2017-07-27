package live.itrip.app.service.net;

import live.itrip.app.data.net.response.BlogResultResp;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Feng on 2017/7/24.
 */

public interface BlogService {
    /**
     * get blog detail
     *
     * @param blogId
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("blog/{id}")
    Observable<BlogResultResp> getBlogDetail(@Path("id") Long blogId);
}
