package live.itrip.app.service.FileLoads;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Feng on 2017/6/20.
 */

public class ServiceGenerator {
    private static final String HOST = "http://www.xxx.com/ ";

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(HOST)
//            .addConverterFactory(ProtoConverterFactory.create())
//            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());


    public static <T> T createService(Class<T> tClass) {
        return builder.build().create(tClass);
    }


    /**
     * 创建带响应进度(下载进度)回调的service
     */
//    public static <T> T createResponseService(Class<T> tClass, ProgressResponseListener listener) {
//        return builder
//                .client(new HttpClientHelper().addProgressResponseListener(listener))
//                .build()
//                .create(tClass);
//    }


    /**
     * 创建带请求体进度(上传进度)回调的service
     */
//    public static <T> T createReqeustService(Class<T> tClass, ProgressRequestListener listener) {
//        return builder
//                .client(new HttpClientHelper().addProgressRequestListener(listener))
//                .build()
//                .create(tClass);
//    }
}
