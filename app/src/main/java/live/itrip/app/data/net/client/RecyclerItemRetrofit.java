package live.itrip.app.data.net.client;

import javax.inject.Inject;

import live.itrip.app.data.net.client.cache.RecyclerViewCacheHttpClient;
import live.itrip.app.data.net.client.core.ApiEndpoint;
import live.itrip.app.data.net.client.core.BaseRetrofit;
import okhttp3.OkHttpClient;

/**
 * Created by Feng on 2017/6/26.
 */

public class RecyclerItemRetrofit extends BaseRetrofit {

    RecyclerViewCacheHttpClient mHttpClient;

    @Inject
    public RecyclerItemRetrofit(RecyclerViewCacheHttpClient httpClient) {
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
        return this.mHttpClient.get();
    }


}
