package live.itrip.app.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import live.itrip.app.R;
import live.itrip.app.ui.util.DeviceUtils;

/**
 * Created by Feng on 2017/7/5.
 */

public class IdentityView extends AppCompatTextView {
    private static final int STROKE_SIZE = 2;
    private int mColor = 0xff24CF5F;
    private boolean mWipeOffBorder = false;
//    private Author.Identity mIdentity;
    private GradientDrawable mDrawable;

    public IdentityView(Context context) {
        super(context);
        init(null, 0);
    }

    public IdentityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public IdentityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        Context context = getContext();

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IdentityView, defStyleAttr, 0);
            mColor = a.getColor(R.styleable.IdentityView_oscColor, mColor);
            mWipeOffBorder = a.getBoolean(R.styleable.IdentityView_oscWipeOffBorder, mWipeOffBorder);
            a.recycle();
        }

        setVisibility(GONE);
        setTextSize(10);
        setGravity(Gravity.CENTER);
        setSingleLine(true);
        setLines(1);
        setColor(mColor);
        setText(R.string.app_name);

        final int padding = (int) DeviceUtils.dipToPx(getResources(), 2);
        setPadding(padding + padding, padding, padding + padding, padding);

//        if (isInEditMode()) {
//            Author.Identity identity = new Author.Identity();
//            identity.officialMember = true;
//            setup(identity);
//        }
    }

    public void setColor(int color) {
        mColor = color;
        final GradientDrawable drawable = mDrawable;
        if (drawable != null) {
            drawable.setStroke(STROKE_SIZE, color);
        }
        setTextColor(color);
        invalidate();
    }

//    public void setup(Author author) {
//        if (author == null)
//            setup((Author.Identity) null);
//        else
//            setup(author.getIdentity());
//    }
//
//    public void setup(Author.Identity identity) {
//        this.mIdentity = identity;
//
//        if (identity == null) {
//            setVisibility(GONE);
//            return;
//        }
//
//        setVisibility(identity.officialMember ? VISIBLE : GONE);
//        initBorder();
//    }

    private void initBorder() {
//        if (mWipeOffBorder || mIdentity == null || !mIdentity.officialMember) {
//            mDrawable = null;
//            setBackground(null);
//            return;
//        }

        if (mDrawable == null) {
            float radius = 4f;

            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setDither(true);
            gradientDrawable.setStroke(STROKE_SIZE, mColor);
            gradientDrawable.setCornerRadius(radius);

            mDrawable = gradientDrawable;
        } else {
            mDrawable.setStroke(STROKE_SIZE, mColor);
        }

        setBackground(mDrawable);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        final GradientDrawable drawable = mDrawable;
        if (drawable != null) {
            drawable.setCornerRadius(4);
        }
    }
}