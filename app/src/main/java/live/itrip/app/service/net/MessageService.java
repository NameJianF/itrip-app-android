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

    /**
     * 消息列表
     *
     * @param type
     * @param uid
     * @param page
     * @param pageSize
     * @param lastMsgId
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("msg/list")
    Observable<MessageResultResp> getMessages(@Query("type") int type
            , @Query("uid") Long uid
            , @Query("page") int page
            , @Query("pageSize") int pageSize
            , @Query("lastMsgId") Long lastMsgId);

    /**
     * 消息具体信息
     *
     * @param msgId
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("msg/{id}")
    Observable<MessageModel> getMessageDetail(@Path("id") Long msgId);


    /**
     * 对话消息
     *
     * @param fromUserId
     * @param toUserId
     * @return
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("msg/dia")
    Observable<MessageResultResp> loadDialogMesages(@Query("fromId") Long fromUserId
            , @Query("toId") Long toUserId
            , @Query("lastMsgId") Long lastMsgId);
}
