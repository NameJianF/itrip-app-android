package live.itrip.app.data.net;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;

import java.util.List;

import javax.inject.Inject;

import live.itrip.app.config.AppConfig;
import live.itrip.app.config.Constants;
import live.itrip.app.data.api.FeedBackApi;
import live.itrip.app.data.net.client.FeedBackRetrofit;
import live.itrip.app.data.net.request.FeedBackParams;
import live.itrip.app.data.net.response.ResultResp;
import live.itrip.app.service.net.FeedBackService;
import live.itrip.common.util.SigUtil;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created on 2017/9/6.
 *
 * @author JianF
 *
 */
public class FeedBackDataSource implements FeedBackApi {

    @Inject
    FeedBackRetrofit mFeedBackRetrofit;

    @Inject
    public FeedBackDataSource() {
    }

    @Override
    public Observable<ResultResp> submitFeedBackMessages(int feedType, String message, List<String> imageUrlList, String userName) throws JSONException {
        FeedBackParams request = new FeedBackParams();
        request.setOp(Constants.ApiOp.OP_FEEDBACK_SUBMIT);
        request.setFeedType(feedType);
        request.setMessage(message);
        request.setImageUrlList(imageUrlList);
        request.setUserName(userName);

        String json = JSON.toJSONString(request);
        request.setSig(SigUtil.getSig(json, AppConfig.SECRET_KEY));

        mFeedBackRetrofit.setPostJsonString(JSON.toJSONString(request));

        final FeedBackService service = mFeedBackRetrofit.get().create(FeedBackService.class);

        Observable<ResultResp> respObservable = service.submitFeedBackMessages();
        return respObservable.map(new Func1<ResultResp, ResultResp>() {
            @Override
            public ResultResp call(ResultResp resp) {
                return resp;
            }
        });
    }
}
