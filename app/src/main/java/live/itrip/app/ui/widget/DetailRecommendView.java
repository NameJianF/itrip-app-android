package live.itrip.app.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import live.itrip.app.R;
import live.itrip.app.data.model.RecommendModel;
import live.itrip.app.ui.util.UIUtils;

/**
 * Created by Feng on 2017/7/24.
 */

public class DetailRecommendView extends LinearLayout {
    private int mDefaultType;
    private LinearLayout mLayAbouts;
    private TextView mTitle;

    public DetailRecommendView(Context context) {
        super(context);
        init();
    }

    public DetailRecommendView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DetailRecommendView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View inflate = inflater.inflate(R.layout.lay_detail_about_layout, this, true);

        mTitle = (TextView) inflate.findViewById(R.id.tv_blog_detail_about);
        mLayAbouts = (LinearLayout) findViewById(R.id.lay_blog_detail_about);
    }

    /**
     * set title
     *
     * @param title string
     */
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setRecommendModels(List<RecommendModel> recommendList, int defaultType) {
        mLayAbouts.removeAllViews();
        this.mDefaultType = defaultType;
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        if (recommendList != null && recommendList.size() > 0) {
            int size = recommendList.size();
            for (final RecommendModel model : recommendList) {
                if (model == null)
                    continue;
                @SuppressLint("InflateParams")
                View lay = inflater.inflate(R.layout.lay_blog_detail_recommend, null, false);
                ((TextView) lay.findViewById(R.id.tv_title)).setText(model.getTitle());

                View layInfo = lay.findViewById(R.id.lay_info_view_comment);
                layInfo.findViewById(R.id.iv_info_view).setVisibility(GONE);
                ((TextView) layInfo.findViewById(R.id.tv_info_view)).setVisibility(GONE);//setText(String.valueOf(about.getViewCount()));
                ((TextView) layInfo.findViewById(R.id.tv_info_comment)).setText(String.valueOf(model.getCommentCount()));

                if (--size == 0) {
                    lay.findViewById(R.id.line).setVisibility(View.INVISIBLE);
                }

                lay.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int type = mDefaultType;
                        if (model.getType() != 0) {
                            type = model.getType();
                        }
//                        UIUtils.showDetail(v.getContext(), type, model.getId(), null);
                    }
                });

                mLayAbouts.addView(lay);
            }
        } else {
            setVisibility(View.GONE);
        }
    }
}
