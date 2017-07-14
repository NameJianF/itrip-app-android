package live.itrip.app.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import live.itrip.app.R;
import live.itrip.app.data.model.ChildMultiItem;
import live.itrip.app.config.Constants;
import live.itrip.app.data.model.HomePageModel;
import live.itrip.app.ui.util.BannerImageLoader;
import live.itrip.app.ui.util.ToastUtils;
import live.itrip.common.util.StringUtils;

/**
 * Created by Feng on 2017/7/12.
 */

public class HomePageRecyclerAdapter extends BaseMultiItemQuickAdapter<HomePageModel> {


    public HomePageRecyclerAdapter(List<HomePageModel> data) {
        super(data);
        addItemType(HomePageModel.ITEM_BANNER, R.layout.fragment_home_banner);
        addItemType(HomePageModel.ITEM_NAV, R.layout.fragment_home_nav);
        addItemType(HomePageModel.ITEM_HOT, R.layout.fragment_home_hot);
        addItemType(HomePageModel.ITEM_CATEGORY, R.layout.fragment_home_category);
        // test
        addItemType(HomePageModel.ITEM_LIST, R.layout.item_img_text_view);
        addItemType(HomePageModel.ITEM_AD, R.layout.item_img_text_view);
    }

    @Override
    protected void convert(BaseViewHolder holder, HomePageModel homePageModel) {
        switch (holder.getItemViewType()) {
            case HomePageModel.ITEM_BANNER:
                initViewBanner(holder, homePageModel);
                break;
            case HomePageModel.ITEM_NAV:
                initViewNav(holder, homePageModel);
                break;
            case HomePageModel.ITEM_HOT:
                initViewHot(holder, homePageModel);
                break;
            case HomePageModel.ITEM_CATEGORY:
                initViewCategory(holder, homePageModel);
                break;
            case HomePageModel.ITEM_LIST:
                initViewList(holder, homePageModel);
                break;
            case HomePageModel.ITEM_AD:
                initViewAd(holder, homePageModel);
                break;
        }

    }

    private void initViewBanner(BaseViewHolder holder, final HomePageModel homePageModel) {

        final Banner mBanner = holder.getView(R.id.banner);//     @BindView(R.id.banner)
        // banner
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        mBanner.setImageLoader(new BannerImageLoader());
        //设置图片集合
        mBanner.setImages(Arrays.asList(Constants.mBannerUrls));
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(Arrays.asList(Constants.mBannerNames));
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (homePageModel.getItemList() != null) {
                    HomePageModel item = (HomePageModel) homePageModel.getItemList().get(position);
                    ToastUtils.showToast("Position ID : " + item.getId());
                }
            }
        });
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();


    }

    private void initViewNav(BaseViewHolder holder, HomePageModel homePageModel) {
        holder.getView(R.id.relId1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("自由行 clicked.");
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

    private void initViewHot(final BaseViewHolder holder, HomePageModel homePageModel) {
        Picasso.with(this.mContext)
                .load(homePageModel.getImgUrl())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into((ImageView) holder.getView(R.id.hot_img));
        holder.setText(R.id.hot_title_tv, StringUtils.replaceAllBlank(homePageModel.getTitle()));
//        holder.setText(R.id.hot_sub_tv, StringUtils.trimNewLine(homePageModel.getContent()));

        holder.getView(R.id.linearLayout_hot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("Hot clicked.");
            }
        });
    }

    private void initViewCategory(BaseViewHolder holder, HomePageModel homePageModel) {
        if (homePageModel.getItemList() != null) {
            RecyclerView mRecyclerView = holder.getView(R.id.rv_list);
            final ArrayList<ChildMultiItem> data = homePageModel.getItemList();
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

    private void initViewList(BaseViewHolder holder, HomePageModel homePageModel) {
        Picasso.with(this.mContext)
                .load(homePageModel.getImgUrl())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into((ImageView) holder.getView(R.id.iv));
        holder.setText(R.id.tv, StringUtils.trimNewLine(homePageModel.getTitle()));
    }

    private void initViewAd(BaseViewHolder holder, HomePageModel homePageModel) {
        Picasso.with(this.mContext)
                .load(homePageModel.getImgUrl())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into((ImageView) holder.getView(R.id.iv));
        holder.setText(R.id.tv, StringUtils.trimNewLine(homePageModel.getTitle()));
    }

}
