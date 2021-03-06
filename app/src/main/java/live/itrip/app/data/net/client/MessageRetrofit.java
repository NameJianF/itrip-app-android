package live.itrip.app.data.net.client;

import javax.inject.Inject;

import live.itrip.app.data.net.client.cache.MessageCacheHttpClient;
import live.itrip.app.data.net.client.core.ApiEndpoint;
import live.itrip.app.data.net.client.core.BaseRetrofit;
import okhttp3.OkHttpClient;

/**
 * Created by Feng on 2017/6/27.
 */

public class MessageRetrofit extends BaseRetrofit {
    MessageCacheHttpClient mHttpClient;

    @Inject
    public MessageRetrofit(MessageCacheHttpClient httpClient) {
        this.mHttpClient = httpClient;
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
