package live.itrip.app.ui.activity.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.adapter.RecyclerViewAdapter;
import live.itrip.app.data.api.RecyclerItemListApi;
import live.itrip.app.data.model.RecyclerViewItem;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.DaggerRecyclerViewComponent;
import live.itrip.app.di.component.RecyclerViewComponent;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.di.module.RecyclerViewModule;
import live.itrip.app.presenter.RecyclerViewPresenter;
import live.itrip.app.ui.base.BaseLoadingActivity;
import live.itrip.common.mvp.view.LceView;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/6/26.
 */

public class RecyclerViewActivity extends BaseLoadingActivity implements LceView<ArrayList<RecyclerViewItem>>, HasComponent<RecyclerViewComponent> {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Inject
    public RecyclerViewPresenter mRecyclerViewPresenter;

    private RecyclerViewAdapter mRecyclerViewAdapter;

    private static final String ACTION_BUBBLES = "bubbles"; // 冒泡
    private static final String ACTION_FAVORITE = "favorite"; // 收藏
    private static final String ACTION_FOLLOWING = "following"; //关注
    private static final String ACTION_FOLLOWER = "follower"; //粉丝
    private static final String ACTION_BLOGS = "blogs"; //我的博客

    public static void launchToShowBubbles(Context context) {
        Intent intent = new Intent(context, RecyclerViewActivity.class);
        intent.setAction(ACTION_BUBBLES);
        context.startActivity(intent);
    }

    public static void launchToShowFavorite(Context context) {
        Intent intent = new Intent(context, RecyclerViewActivity.class);
        intent.setAction(ACTION_FAVORITE);
        context.startActivity(intent);
    }

    public static void launchToShowFollowing(Context context) {
        Intent intent = new Intent(context, RecyclerViewActivity.class);
        intent.setAction(ACTION_FOLLOWING);
        context.startActivity(intent);
    }

    public static void launchToShowFollower(Context context) {
        Intent intent = new Intent(context, RecyclerViewActivity.class);
        intent.setAction(ACTION_FOLLOWER);
        context.startActivity(intent);
    }

    public static void launchToShowBlogs(Context context) {
        Intent intent = new Intent(context, RecyclerViewActivity.class);
        intent.setAction(ACTION_BLOGS);
        context.startActivity(intent);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        ButterKnife.bind(this);

        initViews();
        mRecyclerViewPresenter.attachView(this);
        loadData();
    }

    private void initViews() {

        mRecyclerViewAdapter = new RecyclerViewAdapter(null);
        mRecyclerViewAdapter.setOnRecyclerViewItemClickListener(mItemtClickListener);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .color(Color.TRANSPARENT)
                .size(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .build());

        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener mItemtClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            RecyclerViewItem item = mRecyclerViewAdapter.getItem(position);
//            RepoDetailActivity.launch(RecyclerViewActivity.this, item.getOwner().getLogin(), item.getName());
        }
    };


    private void loadData() {
        // test datas
        ArrayList<RecyclerViewItem> list = new ArrayList<RecyclerViewItem>();

        String action = getIntent().getAction();
//        String username = getIntent().getStringExtra(EXTRA_USER_NAME);

        String toolbarTitle = "";
        if (ACTION_BUBBLES.equals(action)) {
            toolbarTitle = "我的冒泡";

            mRecyclerViewPresenter.loadItemList(RecyclerItemListApi.FLAG_BUBBLES, 1);
            for (int i = 0; i < 20; i++) {
                RecyclerViewItem item = new RecyclerViewItem();
                item.setId(i + 1);
                item.setTitle("title " + (i + 1));
                item.setDesc("desc " + (i + 1));
                item.setIamgePath("");

                list.add(item);
            }

        } else if (ACTION_FAVORITE.equals(action)) {
            toolbarTitle = "我的收藏";
            mRecyclerViewPresenter.loadItemList(RecyclerItemListApi.FLAG_FAVORITE, 1);
            for (int i = 0; i < 20; i++) {
                RecyclerViewItem item = new RecyclerViewItem();
                item.setId(i + 1);
                item.setTitle("title " + (i + 1));
                item.setDesc("desc " + (i + 1));
                item.setIamgePath("");

                list.add(item);
            }
        } else if (ACTION_FOLLOWING.equals(action)) {
            toolbarTitle = "我的关注";

            mRecyclerViewPresenter.loadItemList(RecyclerItemListApi.FLAG_FOLLOWING, 1);
            for (int i = 0; i < 20; i++) {
                RecyclerViewItem item = new RecyclerViewItem();
                item.setId(i + 1);
                item.setTitle("title " + (i + 1));
                item.setDesc("desc " + (i + 1));
                item.setIamgePath("");

                list.add(item);
            }
        } else if (ACTION_FOLLOWER.equals(action)) {
            toolbarTitle = "我的粉丝";
            mRecyclerViewPresenter.loadItemList(RecyclerItemListApi.FLAG_FOLLOWER, 1);
            for (int i = 0; i < 20; i++) {
                RecyclerViewItem item = new RecyclerViewItem();
                item.setId(i + 1);
                item.setTitle("title " + (i + 1));
                item.setDesc("desc " + (i + 1));
                item.setIamgePath("");

                list.add(item);
            }
        } else if (ACTION_BLOGS.equals(action)) {
            toolbarTitle = "我的博客";
            mRecyclerViewPresenter.loadItemList(RecyclerItemListApi.FLAG_FOLLOWER, 1);
            for (int i = 0; i < 20; i++) {
                RecyclerViewItem item = new RecyclerViewItem();
                item.setId(i + 1);
                item.setTitle("title " + (i + 1));
                item.setDesc("desc " + (i + 1));
                item.setIamgePath("");

                list.add(item);
            }
        }

        this.setActionBarTitle(toolbarTitle);
        this.mRecyclerViewAdapter.setNewData(list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecyclerViewPresenter.detachView();
    }

    @Override
    public String getLoadingMessage() {
        return getString(R.string.loading);
    }


    @Override
    public void showContent(ArrayList<RecyclerViewItem> data) {
        mRecyclerViewAdapter.setNewData(data);
    }

    @Override
    public void showError(Throwable e) {
        AppLog.e(e);
    }

    @Override
    public void showEmpty() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        AppLog.d("trace===RecyclerViewActivity onResume");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public RecyclerViewComponent getComponent() {
        return DaggerRecyclerViewComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .activityModule(new ActivityModule(this))
                .recyclerViewModule(new RecyclerViewModule())
                .build();
    }


}
