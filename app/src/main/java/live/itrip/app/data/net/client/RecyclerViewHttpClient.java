package live.itrip.app.data.net.client;

import android.app.Application;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import javax.inject.Inject;

import live.itrip.app.data.PreferenceData;
import live.itrip.app.data.net.request.RecyclerItemParams;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Feng on 2017/6/26.
 */

public class RecyclerViewHttpClient extends CacheHttpClient {
//    private static final MediaType APPLICATION_JSON
//            = MediaType.parse("application/json; charset=utf-8");

    @Inject
    public Application application;

    @Inject
    public RecyclerViewHttpClient() {
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
                        .header("User-Agent", "");

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
