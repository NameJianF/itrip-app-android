package live.itrip.app.data.net.client;

import javax.inject.Inject;

import live.itrip.app.data.net.client.cache.BlogDetailCacheHttpClient;
import live.itrip.app.data.net.client.core.ApiEndpoint;
import live.itrip.app.data.net.client.core.BaseRetrofit;
import okhttp3.OkHttpClient;

/**
 * Created by Feng on 2017/7/25.
 */

public class BlogDetailRetrofit extends BaseRetrofit {
    BlogDetailCacheHttpClient mHttpClient;


    @Inject
    public BlogDetailRetrofit(BlogDetailCacheHttpClient mHttpClient) {
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
