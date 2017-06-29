package live.itrip.common.files;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Feng on 2017/6/29.
 * Detail
 */

public interface ServicesApi {

    /**
     * 用于测试post
     * @param url
     * @param route
     * @return
     */
    @POST("{url}")
    Observable<ResponseBody> postTest(
            @Path(value = "url", encoded = true) String url,
            @Body RequestBody route
    );

}
