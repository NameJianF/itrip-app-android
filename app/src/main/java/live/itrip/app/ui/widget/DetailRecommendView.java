package live.itrip.app.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import live.itrip.app.R;
import live.itrip.app.bean.Recommend;
import live.itrip.app.ui.fragment.plan.PlanDetailFragment;
import live.itrip.app.util.PicassoImageLoader;
import live.itrip.app.util.UIUtils;
import live.itrip.common.util.StringUtils;

/**
 * Created by Feng on 2017/7/24.
 */
public class DetailRecommendView extends LinearLayout {

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

    /**
     * @param recommendList
     * @param viewType
     */
    public void setRecommendModels(List<Recommend> recommendList, ViewType viewType) {
        mLayAbouts.removeAllViews();
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        if (recommendList != null && recommendList.size() > 0) {
            int size = recommendList.size();
            for (final Recommend model : recommendList) {
                if (model == null)
                    continue;
                View lay = null;
                if (ViewType.BLOG.equals(viewType)) {
                    lay = setItemViewBlog(inflater, model, size);
                } else if (ViewType.PLAN.equals(viewType)) {
                    lay = setItemViewPlan(inflater, model, size);
                }

                if (lay != null) {
                    mLayAbouts.addView(lay);
                }
            }
        } else {
            setVisibility(View.GONE);
        }
    }

    /**
     * 设置博客详情下相关推荐
     *
     * @param inflater
     * @param model
     * @param size
     * @return
     */
    private View setItemViewBlog(LayoutInflater inflater, Recommend model, int size) {
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
//                        int type = mDefaultType;
//                        if (model.getType() != 0) {
//                            type = model.getType();
//                        }
//                        UIUtils.showDetail(v.getContext(), type, model.getId(), null);
            }
        });
        return lay;
    }

    /**
     * 设置行程下相关推荐
     *
     * @param inflater
     * @param model
     * @param size
     * @return
     */
    private View setItemViewPlan(LayoutInflater inflater, final Recommend model, int size) {
        @SuppressLint("InflateParams")
        View lay = inflater.inflate(R.layout.item_plan_recommend_view, null, false);

        // image
        ImageView imageView = (ImageView) lay.findViewById(R.id.image_view_title);
        PicassoImageLoader.getInstance().showImage(getContext(), model.getImageUrl(), imageView);
        // title
        ((TextView) lay.findViewById(R.id.text_view_title)).setText(model.getTitle());
        // subtitle
        ((TextView) lay.findViewById(R.id.text_view_subtitle)).setText(model.getSubTitle());
        // price
        String price = StringUtils.trimNewLine(String.format("¥ %s", model.getPrice()));
        ((TextView) lay.findViewById(R.id.text_view_price)).setText(price);
        // Participate
        String participate = StringUtils.trimNewLine(String.format("%s 参与", model.getParticipate()));
        ((TextView) lay.findViewById(R.id.text_view_participate)).setText(participate);

        if (--size == 0) {
            lay.findViewById(R.id.line).setVisibility(View.INVISIBLE);
        }

        lay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong(PlanDetailFragment.EXTRA_PLAN_ID, model.getId());
                UIUtils.showPlanDetailActivity(getContext(), bundle);
            }
        });
        return lay;
    }

    public static enum ViewType {
        BLOG, PLAN
    }
}
