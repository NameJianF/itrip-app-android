package live.itrip.app.data.net.client;

import javax.inject.Inject;

import live.itrip.app.data.net.client.cache.FeedBackCacheHttpClient;
import live.itrip.app.data.net.client.core.ApiEndpoint;
import live.itrip.app.data.net.client.core.BaseRetrofit;
import okhttp3.OkHttpClient;

/**
 * Created by Feng on 2017/9/6.
 */

public class FeedBackRetrofit extends BaseRetrofit {
    FeedBackCacheHttpClient mHttpClient;
    private String postJsonString;

    @Inject
    public FeedBackRetrofit(FeedBackCacheHttpClient httpClient) {
        this.mHttpClient = httpClient;
    }


    public void setPostJsonString(String postJsonString) {
        this.postJsonString = postJsonString;
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
        this.mHttpClient.setPostJsonString(this.postJsonString);
        return mHttpClient.get();
    }
}
