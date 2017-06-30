package live.itrip.app.service.net;

import live.itrip.app.data.net.response.MessageResultResp;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

/**
 * Created by Feng on 2017/6/27.
 */

public interface MessageService {

    @GET("list/msg?uid=1&page=1&pageSize=20&lastMsgId=0")
    Observable<MessageResultResp> getMessages();

}
