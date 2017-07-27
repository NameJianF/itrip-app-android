package live.itrip.app.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import live.itrip.app.ui.util.UIUtils;

/**
 * Created by Feng on 2017/7/24.
 * <p>
 * 判断View是否在屏幕范围内
 */

public class ScreenView extends View {
    public ScreenView(Context context) {
        super(context);
    }

    public ScreenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isViewInScreen() {

        int screenWidth = UIUtils.getScreenWidth(getContext());

        int screenHeight = UIUtils.getScreenHeight(getContext());

        Rect rect = new Rect(0, 0, screenWidth, screenHeight);

        int[] location = new int[2];

        getLocationInWindow(location);

        return getLocalVisibleRect(rect);
    }
}