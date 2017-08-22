package live.itrip.app.adapter;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import live.itrip.app.R;
import live.itrip.app.data.model.ChildMultiItem;
import live.itrip.app.data.model.PlanCategoryModel;
import live.itrip.app.ui.fragment.blog.BlogDetailFragment;
import live.itrip.app.ui.fragment.plan.PlanDetailFragment;
import live.itrip.app.util.BannerImageLoader;
import live.itrip.app.util.ToastUtils;
import live.itrip.app.util.UIUtils;
import live.itrip.common.util.StringUtils;

/**
 * Created by Feng on 2017/7/12.
 */

public class PlanCategoryRecyclerAdapter extends BaseMultiItemQuickAdapter<PlanCategoryModel> {

    public PlanCategoryRecyclerAdapter(List<PlanCategoryModel> data) {
        super(data);
        addItemType(PlanCategoryModel.ITEM_BANNER, R.layout.fragment_home_banner);
        addItemType(PlanCategoryModel.ITEM_NAV, R.layout.fragment_home_nav);
        addItemType(PlanCategoryModel.ITEM_NEW_PLAN, R.layout.fragment_home_new_plan);
        addItemType(PlanCategoryModel.ITEM_HOT, R.layout.fragment_home_hot);
        addItemType(PlanCategoryModel.ITEM_LIST, R.layout.fragment_home_hot);
        addItemType(PlanCategoryModel.ITEM_BLOG, R.layout.fragment_home_hot);
        addItemType(PlanCategoryModel.ITEM_AD, R.layout.fragment_home_ad);
    }

    @Override
    protected void convert(BaseViewHolder holder, PlanCategoryModel planCategoryModel) {
        if (holder.getItemViewType() >= 0) {
            switch (holder.getItemViewType()) {
                case PlanCategoryModel.ITEM_BANNER:
                    initViewBanner(holder, planCategoryModel);
                    break;
                case PlanCategoryModel.ITEM_NAV:
                    initViewNav(holder, planCategoryModel);
                    break;
                case PlanCategoryModel.ITEM_NEW_PLAN:
                    initViewNewPlan(holder, planCategoryModel);
                    break;
                case PlanCategoryModel.ITEM_HOT:
                    initViewHot(holder, planCategoryModel);
                    break;
                case PlanCategoryModel.ITEM_LIST:
                    initViewList(holder, planCategoryModel);
                    break;
                case PlanCategoryModel.ITEM_BLOG:
                    initViewBlog(holder, planCategoryModel);
                    break;
                case PlanCategoryModel.ITEM_AD:
                    initViewAD(holder, planCategoryModel);
                    break;
            }
        }
    }


    private void initViewBanner(BaseViewHolder holder, final PlanCategoryModel planCategoryModel) {
        // reset values
        ArrayList<String> urls = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        if (planCategoryModel.getItems() != null) {
            for (ChildMultiItem item : planCategoryModel.getItems()) {
                urls.add(item.getImageUrl());
                titles.add(item.getTitle());
            }
        }

        final Banner mBanner = holder.getView(R.id.banner);
        // banner
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        mBanner.setImageLoader(new BannerImageLoader());
        //设置图片集合
        mBanner.setImages(urls);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (planCategoryModel.getItems() != null) {
                    ChildMultiItem childMultiItem = planCategoryModel.getItems().get(position);
                    Bundle bundle = new Bundle();
                    bundle.putLong(PlanDetailFragment.EXTRA_PLAN_ID, childMultiItem.getId());
                    UIUtils.showPlanDetailActivity(mContext, bundle);
                }
            }
        });
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

    }

    private void initViewNav(BaseViewHolder holder, PlanCategoryModel planCategoryModel) {
        holder.getView(R.id.relId1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showPlanSelfGuided(mContext, null);
//                ToastUtils.showToast("自由行 clicked.");
            }
        });
        holder.getView(R.id.relId2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("主题旅游 clicked.");
            }
        });
        holder.getView(R.id.relId3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("乡村民宿 clicked.");
            }
        });
        holder.getView(R.id.relId4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("旅行服务 clicked.");
            }
        });
    }

    private void initViewNewPlan(final BaseViewHolder holder, PlanCategoryModel planCategoryModel) {
        TextView title = holder.getView(R.id.tvCategoryName);
        title.setText("最新行程");

        ImageView imageView = holder.getView(R.id.new_plan_img);
        Picasso.with(this.mContext)
                .load(planCategoryModel.getImgUrl())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(imageView);
        holder.setText(R.id.new_plan_title_tv, StringUtils.replaceAllBlank(planCategoryModel.getTitle()));

        // image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("hot image clicked.");
            }
        });

        // more click
        holder.getView(R.id.tvCategoryMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("hot more clicked.");
            }
        });
    }

    private void initViewHot(BaseViewHolder holder, PlanCategoryModel planCategoryModel) {
        TextView title = holder.getView(R.id.tvCategoryName);
        title.setText("热门线路");

        if (planCategoryModel.getItems() != null) {
            RecyclerView mRecyclerView = holder.getView(R.id.rv_list);
            final ArrayList<ChildMultiItem> data = planCategoryModel.getItems();
            final MultipleItemQuickAdapter multipleItemAdapter = new MultipleItemQuickAdapter(data);
            final GridLayoutManager manager = new GridLayoutManager(this.mContext, 2);
            mRecyclerView.setLayoutManager(manager);

            multipleItemAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {
                    ToastUtils.showToast("Hot clicked : " + i);
                }
            });

            mRecyclerView.setAdapter(multipleItemAdapter);

        }
    }

    private void initViewList(BaseViewHolder holder, PlanCategoryModel planCategoryModel) {
        TextView title = holder.getView(R.id.tvCategoryName);
        title.setText("猜你喜欢");

        if (planCategoryModel.getItems() != null) {
            RecyclerView mRecyclerView = holder.getView(R.id.rv_list);
            final ArrayList<ChildMultiItem> data = planCategoryModel.getItems();
            final MultipleItemQuickAdapter multipleItemAdapter = new MultipleItemQuickAdapter(data);
            final GridLayoutManager manager = new GridLayoutManager(this.mContext, 2);
            mRecyclerView.setLayoutManager(manager);

            multipleItemAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {
                    ToastUtils.showToast("Hot clicked : " + i);
                }
            });

            mRecyclerView.setAdapter(multipleItemAdapter);

        }
    }

    private void initViewBlog(BaseViewHolder holder, PlanCategoryModel planCategoryModel) {
        TextView title = holder.getView(R.id.tvCategoryName);
        title.setText("推荐博客");

        if (planCategoryModel.getItems() != null) {
            RecyclerView mRecyclerView = holder.getView(R.id.rv_list);
            final ArrayList<ChildMultiItem> data = planCategoryModel.getItems();
            final MultipleItemQuickAdapter multipleItemAdapter = new MultipleItemQuickAdapter(data);
            final GridLayoutManager manager = new GridLayoutManager(this.mContext, 2);
            mRecyclerView.setLayoutManager(manager);

            multipleItemAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {
//                    ToastUtils.showToast("Hot clicked : " + i);

                    ChildMultiItem item = data.get(i);
                    Bundle bundle = new Bundle();
                    bundle.putLong(BlogDetailFragment.EXTRA_BLOG_ID, item.getId());
                    UIUtils.showBlogDetailActivity(mContext, bundle);
                }
            });

            mRecyclerView.setAdapter(multipleItemAdapter);

        }
    }

    private void initViewAD(BaseViewHolder holder, PlanCategoryModel planCategoryModel) {

        ImageView imageView = holder.getView(R.id.ad_img);
        Picasso.with(this.mContext)
                .load(planCategoryModel.getImgUrl())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(imageView);
        // image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("ad image clicked.");
            }
        });

    }

}
