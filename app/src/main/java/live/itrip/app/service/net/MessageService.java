package live.itrip.app.service.net;

import live.itrip.app.data.net.response.MessageResultResp;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

/**
 * Created by Feng on 2017/6/27.
 */

public interface MessageService {
    @Headers("Cache-Control: public, max-age=600")
    @GET("messages?languages[]=java&languages[]=swift")
    Observable<MessageResultResp> getMessages();


}
