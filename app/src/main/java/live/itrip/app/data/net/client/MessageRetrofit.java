package live.itrip.app.data.net.client;

import javax.inject.Inject;

import live.itrip.app.data.net.client.core.ApiEndpoint;
import live.itrip.app.data.net.client.core.BaseRetrofit;
import okhttp3.OkHttpClient;

/**
 * Created by Feng on 2017/6/27.
 */

public class MessageRetrofit extends BaseRetrofit {
    private static final String END_POINT = "http://githubtrending.herokuapp.com/";

    CacheHttpClient mHttpClient;

    @Inject
    public MessageRetrofit(CacheHttpClient mHttpClient) {
        this.mHttpClient = mHttpClient;
    }

    @Override
    public ApiEndpoint getApiEndpoint() {
        return new ApiEndpoint() {
            @Override
            public String getEndpoint() {
                return END_POINT;
            }
        };
    }

    @Override
    public OkHttpClient getHttpClient() {
        return mHttpClient.get();
    }
}
