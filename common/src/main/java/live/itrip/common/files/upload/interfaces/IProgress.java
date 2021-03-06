package live.itrip.common.files.upload.interfaces;

/**
 * Created by Feng on 2017/6/29.
 * Detail 进度监听 提供给下载与上传进度，依赖主线程
 */
public interface IProgress {

    public void onProgress(long progress, long total, int index, boolean done);

    public void onSucess(String result);

    public void onFailed(Throwable e, String reason);
}
