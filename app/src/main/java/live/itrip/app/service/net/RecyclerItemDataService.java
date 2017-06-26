package live.itrip.app.service.net;

import live.itrip.app.data.net.response.RecyclerItemResultResp;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Feng on 2017/6/26.
 */

public interface RecyclerItemDataService {

    /**
     * @param type
     * @param sort
     * @param order
     * @param page
     * @param pageSize
     * @return
     */
    @Headers("Cache-Control: public, max-age=600")
    @GET("search/repositories")
    Observable<RecyclerItemResultResp> getItemList(@Query("q") int type, @Query("sort") String sort,
                                                   @Query("order") String order, @Query("page") int page,
                                                   @Query("pageSize") int pageSize);
}
