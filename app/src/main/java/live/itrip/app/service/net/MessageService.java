package live.itrip.app.service.net;

import live.itrip.app.data.model.MessageModel;
import live.itrip.app.data.net.response.MessageResultResp;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Feng on 2017/6/27.
 */

public interface MessageService {

    @Headers("Cache-Control: public, max-age=3600")
    @GET("msg/list")
    Observable<MessageResultResp> getMessages(@Query("type") int type
            , @Query("uid") Long uid
            , @Query("page") int page
            , @Query("pageSize") int pageSize
            , @Query("lastMsgId") Long lastMsgId);


    @Headers("Cache-Control: public, max-age=3600")
    @GET("msg/{id}")
    Observable<MessageModel> getMessageDetail(@Path("id") Long msgId);

}
