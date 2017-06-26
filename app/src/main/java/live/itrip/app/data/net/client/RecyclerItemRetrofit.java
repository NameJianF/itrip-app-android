package live.itrip.app.data.net.client;

import javax.inject.Inject;

import live.itrip.app.data.net.client.core.ApiEndpoint;
import live.itrip.app.data.net.client.core.BaseRetrofit;
import live.itrip.app.data.net.request.RecyclerItemParams;
import okhttp3.OkHttpClient;

/**
 * Created by Feng on 2017/6/26.
 */

public class RecyclerItemRetrofit extends BaseRetrofit {
    private static final String END_POINT = "http://admin.itrip.live/";

//    private RecyclerItemParams recyclerItemParams;

    RecyclerViewHttpClient mHttpClient;

    @Inject
    public RecyclerItemRetrofit(RecyclerViewHttpClient httpClient) {
        this.mHttpClient = httpClient;
    }

//    public void setRecyclerItemParams(RecyclerItemParams params) {
//        this.recyclerItemParams = params;
//    }

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
