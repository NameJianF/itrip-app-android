package live.itrip.app.data.net.client.core;

import java.util.concurrent.TimeUnit;

import live.itrip.app.BuildConfig;
import live.itrip.app.service.FileLoads.ProgressRequestListener;
import live.itrip.app.service.FileLoads.ProgressResponseListener;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by JianF on 16/7/20.
 */
public abstract class BaseOkHttpClient {

    private static final long TIMEOUT_CONNECT = 30 * 1000;

    public OkHttpClient get() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(BuildConfig.DEBUG ?
                                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE));

        builder = customize(builder);

        return builder.build();
    }

    /**
     * 用于数据
     *
     * @param builder
     * @return
     */
    public abstract OkHttpClient.Builder customize(OkHttpClient.Builder builder);

    /**
     * 包装OkHttpClient，用于下载文件的回调
     *
     * @param progressListener
     * @return
     */
    public abstract OkHttpClient addProgressResponseListener(ProgressResponseListener progressListener);

    /**
     * 包装OkHttpClient，用于上传文件的回调
     *
     * @param progressListener
     * @return
     */
    public abstract OkHttpClient addProgressRequestListener(ProgressRequestListener progressListener);
}
