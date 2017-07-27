package live.itrip.app.ui.widget.listener;

/**
 * Created by Feng on 2017/7/21.
 */

public interface OnWebViewImageListener {
    /**
     * 点击webview上的图片，传入该缩略图的大图Url
     *
     * @param bigImageUrl
     */
    void showImagePreview(String bigImageUrl);
}
