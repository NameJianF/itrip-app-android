package live.itrip.app.data.net.client.cache;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Feng on 2017/9/6.
 */

public class FeedBackCacheHttpClient extends CacheHttpClient {
    @Inject
    public FeedBackCacheHttpClient() {
    }

    public MediaType getAcceptHeader() {
        return MediaType.parse("application/json; charset=utf-8");
    }


    private String postJsonString;

    public void setPostJsonString(String postJsonString) {
        this.postJsonString = postJsonString;
    }

    @Override
    public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {
        builder = super.customize(builder);

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                RequestBody body = RequestBody.create(getAcceptHeader(), postJsonString.trim());
                Request.Builder requestBuilder = original.newBuilder().post(body);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return builder;
    }
}
