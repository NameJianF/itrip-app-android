package live.itrip.app.data.net.client;

import javax.inject.Inject;

import live.itrip.app.data.net.client.cache.CacheHttpClient;
import live.itrip.app.data.net.client.core.ApiEndpoint;
import live.itrip.app.data.net.client.core.BaseRetrofit;
import okhttp3.OkHttpClient;

/**
 * Created by Feng on 2017/6/28.
 */

public class SettingRetrofit extends BaseRetrofit {

    CacheHttpClient mHttpClient;

    @Inject
    public SettingRetrofit(CacheHttpClient mHttpClient) {
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
