package live.itrip.app.data.net.client;

import javax.inject.Inject;

import live.itrip.app.data.net.client.cache.PlanDetailCacheHttpClient;
import live.itrip.app.data.net.client.core.ApiEndpoint;
import live.itrip.app.data.net.client.core.BaseRetrofit;
import okhttp3.OkHttpClient;

/**
 * Created by Feng on 2017/7/25.
 */

public class PlanDetailRetrofit extends BaseRetrofit {
    PlanDetailCacheHttpClient mHttpClient;


    @Inject
    public PlanDetailRetrofit(PlanDetailCacheHttpClient mHttpClient) {
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
