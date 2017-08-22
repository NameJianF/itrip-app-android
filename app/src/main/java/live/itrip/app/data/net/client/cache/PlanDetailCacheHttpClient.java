package live.itrip.app.data.net.client.cache;

import java.io.IOException;

import javax.inject.Inject;

import live.itrip.app.cache.SharePreferenceData;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Feng on 2017/7/25.
 */

public class PlanDetailCacheHttpClient extends CacheHttpClient {
    @Inject
    public PlanDetailCacheHttpClient() {
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

                if (SharePreferenceData.Account.isLogon(mContext)) {
                    requestBuilder
                            .header("Authorization", "token " + SharePreferenceData.Account.getLogonToken(mContext));
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return builder;
    }
}
