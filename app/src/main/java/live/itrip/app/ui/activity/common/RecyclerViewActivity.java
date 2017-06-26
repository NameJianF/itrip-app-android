package live.itrip.app.ui.activity.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
    private ActionBar mActionBar;

    @BindView(R.id.model_list)
    RecyclerView mRecyclerView;
    @Inject
    public RecyclerViewPresenter mRecyclerViewPresenter;

    private RecyclerViewAdapter mRecyclerViewAdapter;

    private static final String EXTRA_USER_NAME = "extra_user_name";
    private static final String ACTION_BUBBLES = "bubbles";

    public static void launchToShowBubbles(Context context, String username) {
        Intent intent = new Intent(context, RecyclerViewActivity.class);
        intent.putExtra(EXTRA_USER_NAME, username);
        intent.setAction(ACTION_BUBBLES);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        initViews();
        mRecyclerViewPresenter.attachView(this);
        loadData();
    }

    private void initViews() {
        mActionBar = this.getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setTitle("我的泡泡");
        }

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
//        ArrayList<RecyclerViewItem> list = new ArrayList<RecyclerViewItem>();
//
//        for (int i = 0; i < 20; i++) {
//            RecyclerViewItem item = new RecyclerViewItem();
//            item.setId(i + 1);
//            item.setTitle("title " + (i + 1));
//            item.setDesc("desc " + (i + 1));
//            item.setIamgePath("");
//
//            list.add(item);
//        }
//        this.mRecyclerViewAdapter.setNewData(list);


        String action = getIntent().getAction();

        String username = getIntent().getStringExtra(EXTRA_USER_NAME);

        if (ACTION_BUBBLES.equals(action)) {
            setTitle("");
            mRecyclerViewPresenter.loadItemList(RecyclerItemListApi.FLAG_BUBBLES, 1);
        } else if ("".equals(action)) {
        }
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
        AppLog.d("trace===FeedBackActivity onResume");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public RecyclerViewComponent getComponent() {
        return DaggerRecyclerViewComponent.builder()
                .applicationComponent(App.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .recyclerViewModule(new RecyclerViewModule())
                .build();
    }


}