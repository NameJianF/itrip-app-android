package live.itrip.common.files.download.interfaces;

/**
 * Created by Feng on 2017/6/29.
 */
public interface IdownLoadProgress {

    public void onProgress(long progress, long total, boolean done);

    // 成功回调保存地址
    public void onSucess(String filePath);

    public void onFailed(Throwable e, String reason);

}
