package live.itrip.common.files.upload.model;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Feng on 2017/6/29.
 * Detail 支持文件上传header
 */
public final class UploadFileFactory implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "multipart/form-data")
                .build();
        Log.e("yuan",request.url().toString());
        return chain.proceed(request);
    }

    public static UploadFileFactory create(){
        return new UploadFileFactory();
    }
}
