package live.itrip.app.service.net;

import live.itrip.app.config.Constants;
import live.itrip.app.data.net.response.ResultResp;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Feng on 2017/9/6.
 */

public interface FeedBackService {

    @POST(Constants.ApiAction.ACTION_FEEDBACK)
    Observable<ResultResp> submitFeedBackMessages();

}
