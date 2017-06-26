package live.itrip.app.data.net.client;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import javax.inject.Inject;

import live.itrip.app.data.net.client.core.ApiEndpoint;
import live.itrip.app.data.net.client.core.BaseOkHttpClient;
import live.itrip.app.data.net.client.core.BaseRetrofit;
import live.itrip.app.data.net.request.CreateAuthorization;
import live.itrip.app.service.FileLoads.ProgressRequestListener;
import live.itrip.app.service.FileLoads.ProgressResponseListener;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Feng on 2017/4/26.
 */
public class AppAuthRetrofit extends BaseRetrofit {

    private static final String END_POINT = "http://admin.itrip.live/";
    public static final MediaType APPLICATION_JSON
            = MediaType.parse("application/json; charset=utf-8");

    private CreateAuthorization createAuthorization;

    @Inject
    public AppAuthRetrofit() {
    }

    public void setAuthInfo(CreateAuthorization createAuthorization) {
        this.createAuthorization = createAuthorization;
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
        return new AuthHttpClient(createAuthorization).get();
    }

    private class AuthHttpClient extends BaseOkHttpClient {
        private CreateAuthorization createAuthorization;

        public AuthHttpClient(CreateAuthorization createAuthorization) {
            this.createAuthorization = createAuthorization;
        }

        @Override
            public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {

            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    RequestBody body = RequestBody.create(APPLICATION_JSON, JSON.toJSONString(createAuthorization).trim());
                    Request.Builder requestBuilder = original.newBuilder().post(body);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            return builder;
        }

    }
}

