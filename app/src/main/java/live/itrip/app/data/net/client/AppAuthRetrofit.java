package live.itrip.app.data.net.client;

import java.io.IOException;

import javax.inject.Inject;

import live.itrip.app.data.net.client.core.ApiEndpoint;
import live.itrip.app.data.net.client.core.BaseOkHttpClient;
import live.itrip.app.data.net.client.core.BaseRetrofit;
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
    // test url
//    private static final String END_POINT_ADMIN = "http://10.32.22.209:8080/";
    private static final String END_POINT_ADMIN = "http://sso.tourin.cn/";
    public static final MediaType APPLICATION_JSON
            = MediaType.parse("application/json; charset=utf-8");

    private String postJsonString;

    @Inject
    public AppAuthRetrofit() {
    }

    public void setPostJsonString(String postJsonString) {
        this.postJsonString = postJsonString;
    }

    @Override
    public ApiEndpoint getApiEndpoint() {
        return new ApiEndpoint() {
            @Override
            public String getEndpoint() {
                return END_POINT_ADMIN;
            }
        };
    }

    @Override
    public OkHttpClient getHttpClient() {
        return new AuthHttpClient(postJsonString).get();
    }

    private class AuthHttpClient extends BaseOkHttpClient {
        private String postJsonString;

        public AuthHttpClient(String postJsonString) {
            this.postJsonString = postJsonString;
        }

        @Override
        public OkHttpClient.Builder customize(OkHttpClient.Builder builder) {

            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    RequestBody body = RequestBody.create(APPLICATION_JSON, postJsonString.trim());
                    Request.Builder requestBuilder = original.newBuilder().post(body);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            return builder;
        }

    }
}

