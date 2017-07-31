package live.itrip.app.data.net.client.cache;

import android.app.Application;

import java.io.IOException;

import javax.inject.Inject;

import live.itrip.app.data.PreferenceData;
import live.itrip.app.data.net.client.cache.CacheHttpClient;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Feng on 2017/6/26.
 */

public class RecyclerViewCacheHttpClient extends CacheHttpClient {

    @Inject
    public Application application;

    @Inject
    public RecyclerViewCacheHttpClient() {
    }

    public String getAcceptHeader() {
        return "application/json; charset=utf-8";
    }

    @Override
    public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {
        builder = super.customize(builder);

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", getAcceptHeader())
                        .header("UserModel-Agent", "itrip");

                if (PreferenceData.Account.isLogon(application)) {
                    requestBuilder
                            .header("Authorization", "token " + PreferenceData.Account.getLogonToken(application));
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return builder;
    }
}
