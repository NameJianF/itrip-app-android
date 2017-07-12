package live.itrip.app.ui.util;


import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import live.itrip.app.R;

/**
 * Created by Feng on 2017/7/12.
 */

public class PicassoImageLoader {
    private static PicassoImageLoader instance;

    public static PicassoImageLoader getInstance() {
        if (instance == null) {
            instance = new PicassoImageLoader();
        }
        return instance;
    }

    //网络图片加载 placeholder
    public void showImage(Context context, String url, ImageView imageView) {
        //要先判断url是否为空，不然Picasso会报异常
        if (!TextUtils.isEmpty(url)) {
            Picasso
                    .with(context)
                    .load(url)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.place_holder)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.place_holder);
        }

    }
}
