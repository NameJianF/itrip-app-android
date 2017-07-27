package live.itrip.app.ui.widget.emoji;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.ViewParent;

import live.itrip.app.ui.widget.face.FaceRecyclerView;

/**
 * Created by Feng on 2017/7/26.
 */

public class EmojiRecyclerView extends FaceRecyclerView {

    EmojiRecyclerView(Context context, FaceRecyclerView.OnFaceClickListener listener) {
        super(context, listener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ViewParent parent = this;
        while (!((parent = parent.getParent()) instanceof ViewPager)) ;
        parent.requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}