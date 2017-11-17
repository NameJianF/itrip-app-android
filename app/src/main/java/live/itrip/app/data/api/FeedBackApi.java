package live.itrip.app.data.api;

import org.json.JSONException;

import java.util.List;

import live.itrip.app.data.net.response.ResultResp;
import rx.Observable;

/**
 * Created by Feng on 2017/9/6.
 */
public interface FeedBackApi {

    Observable<ResultResp> submitFeedBackMessages(int feedType, String message, List<String> imageUrlList, String userName) throws JSONException;

}
