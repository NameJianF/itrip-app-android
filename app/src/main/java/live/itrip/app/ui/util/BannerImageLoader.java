package live.itrip.app.ui.util;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * Created by Feng on 2017/7/12.
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        PicassoImageLoader.getInstance().showImage(context, path.toString(), imageView);
    }
}
