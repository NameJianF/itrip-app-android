package live.itrip.app.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wang.avi.AVLoadingIndicatorView;

import live.itrip.app.R;
import live.itrip.app.util.DeviceUtils;

/**
 * Created by Feng on 2017/7/21.
 */

public class EmptyLayout extends LinearLayout implements View.OnClickListener {

    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;
    public static final int NODATA_ENABLE_CLICK = 5;
    public static final int NO_LOGIN = 6;

    //    @BindView(R.id.img_error_layout)
    ImageView mImageViewError;
    //    @BindView(R.id.loading_indicator_view)
    AVLoadingIndicatorView mLoadingView;
    //    @BindView(R.id.tv_error_layout)
    TextView mTextViewMessage;

    private boolean clickEnable = true;
    private final Context context;

    private android.view.View.OnClickListener listener;
    private int mErrorState;
    private String strNoDataContent = "";

    public EmptyLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_error_layout, this, false);

        mImageViewError = (ImageView) view.findViewById(R.id.img_error_layout);
        mTextViewMessage = (TextView) view.findViewById(R.id.tv_error_layout);
        mLoadingView = (AVLoadingIndicatorView) view.findViewById(R.id.loading_indicator_view);

        setBackgroundColor(-1);
        setOnClickListener(this);
//        mLoadingView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (clickEnable) {
//                    // setErrorType(NETWORK_LOADING);
//                    if (listener != null)
//                        listener.onClick(v);
//                }
//            }
//        });
        addView(view);
    }


    public void dismiss() {
        mErrorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
        mLoadingView.hide();
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoadError() {
        return mErrorState == NETWORK_ERROR;
    }

    public boolean isLoading() {
        return mErrorState == NETWORK_LOADING;
    }

    @Override
    public void onClick(View v) {
        if (clickEnable) {
            // setErrorType(NETWORK_LOADING);
            if (listener != null)
                listener.onClick(v);
        }
    }


    public void setErrorMessage(String msg) {
        mTextViewMessage.setText(msg);
    }

    public void setErrorImag(int imgResource) {
        try {
            mImageViewError.setImageResource(imgResource);
        } catch (Exception e) {
        }
    }

    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case NETWORK_ERROR:
                mErrorState = NETWORK_ERROR;
                if (DeviceUtils.hasInternet()) {
                    mTextViewMessage.setText(R.string.error_view_load_error_click_to_refresh);
                    mImageViewError.setBackgroundResource(R.mipmap.ic_tip_fail);
                } else {
                    mTextViewMessage.setText(R.string.error_view_network_error_click_to_refresh);
                    mImageViewError.setBackgroundResource(R.mipmap.page_icon_network);
                }
                mImageViewError.setVisibility(View.VISIBLE);

                mLoadingView.hide();
                clickEnable = true;
                break;
            case NETWORK_LOADING:
                mErrorState = NETWORK_LOADING;
                mLoadingView.show();
                mImageViewError.setVisibility(View.GONE);
                mTextViewMessage.setText(R.string.loading);
                clickEnable = false;
                break;
            case NODATA:
                mErrorState = NODATA;
                // img.setBackgroundDrawable(SkinsUtil.getDrawable(context,"page_icon_empty"));
                mImageViewError.setBackgroundResource(R.mipmap.ic_tip_fail);
                mImageViewError.setVisibility(View.VISIBLE);
                mLoadingView.hide();
                setTvNoDataContent();
                clickEnable = true;
                break;
            case HIDE_LAYOUT:
                mLoadingView.hide();
                setVisibility(View.GONE);
                break;
            case NODATA_ENABLE_CLICK:
                mErrorState = NODATA_ENABLE_CLICK;
                mImageViewError.setBackgroundResource(R.mipmap.page_icon_empty);
                // img.setBackgroundDrawable(SkinsUtil.getDrawable(context,"page_icon_empty"));
                mImageViewError.setVisibility(View.VISIBLE);
                mLoadingView.hide();
                setTvNoDataContent();
                clickEnable = true;
                break;
            default:
                break;
        }
    }

    public void setNoDataContent(String noDataContent) {
        strNoDataContent = noDataContent;
    }

    public void setOnLayoutClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setTvNoDataContent() {
        if (!strNoDataContent.equals(""))
            mTextViewMessage.setText(strNoDataContent);
        else
            mTextViewMessage.setText(R.string.error_view_no_data);
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)
            mErrorState = HIDE_LAYOUT;
        super.setVisibility(visibility);
    }
}