package live.itrip.app.data.net.client.core;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Feng on 2017/4/26.
 */

public abstract class BaseRetrofit {
    protected static final String END_POINT = "http://10.32.22.209:8080/";

    public Retrofit get() {
        Retrofit.Builder builder = new Retrofit.Builder();

        builder.baseUrl(getApiEndpoint().getEndpoint())
                .client(getHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }

    public abstract ApiEndpoint getApiEndpoint();

    public abstract OkHttpClient getHttpClient();


}