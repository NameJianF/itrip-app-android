package live.itrip.app.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.R;
import live.itrip.app.adapter.HomePageRecyclerAdapter;
import live.itrip.app.config.Constants;
import live.itrip.app.data.model.HomePageModel;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.presenter.HomePagePresenter;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.app.ui.util.BannerImageLoader;
import live.itrip.common.mvp.view.LceView;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/4/25.
 */

public class HomeFragment extends BaseFragment implements LceView<ArrayList<HomePageModel>> {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Inject
    HomePagePresenter mHomePagePresenter;
    private HomePageRecyclerAdapter mHomePageRecyclerAdapter;


    public static HomeFragment newInstance() {
//        Bundle args = new Bundle();
//        args.putString("msg", text);
        HomeFragment fragment = new HomeFragment();
//        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(MainComponent.class).inject(this);

        mHomePagePresenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHomePagePresenter.loadDatas();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHomePagePresenter.detachView();
    }

    private void initViews() {

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
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

        mRefreshLayout.setOnRefreshListener(mRefreshListener);

        mHomePageRecyclerAdapter = new HomePageRecyclerAdapter(null);
        mHomePageRecyclerAdapter.setOnRecyclerViewItemClickListener(mItemClickListener);
        mHomePageRecyclerAdapter.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this.getActivity())
                .color(Color.TRANSPARENT)
                .size(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .build());

        mRecyclerView.setAdapter(mHomePageRecyclerAdapter);

    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener mItemClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int i) {
            HomePageModel model = mHomePageRecyclerAdapter.getItem(i);

//            DialogMessageActivity.launch(getActivity(), msg.getUserFrom(), msg.getUserTo());
            AppLog.d("Recycler View Item Clicked.");
        }
    };

    @Override
    public void showLoading() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void dismissLoading() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showContent(ArrayList<HomePageModel> data) {
        AppLog.d("data:" + data);
        if (data != null) {
            //

            mHomePageRecyclerAdapter.setNewData(data);
        }
    }

    @Override
    public void showError(Throwable e) {
        AppLog.e(e);
    }

    @Override
    public void showEmpty() {
        // TODO
    }

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            AppLog.d("onRefresh, HomeFragement.");

            mHomePagePresenter.loadDatas();
        }
    };
}
