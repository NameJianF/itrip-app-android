package live.itrip.app.ui.activity;

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
import live.itrip.app.adapter.CommonModelListRecyclerAdapter;
import live.itrip.app.data.api.CommonModelApi;
import live.itrip.app.data.model.CommonModel;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.CommonModelComponent;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.di.module.CommonModelModule;
import live.itrip.app.presenter.CommonModelListPresenter;
import live.itrip.app.ui.base.BaseLoadingActivity;
import live.itrip.common.mvp.view.LceView;

/**
 * Created by Feng on 2017/6/22.
 */
public class CommonModelListActivity extends BaseLoadingActivity implements LceView<ArrayList<CommonModel>>
        , HasComponent<CommonModelComponent> {
    private ActionBar mActionBar;

    @BindView(R.id.model_list)
    RecyclerView mRepoListView;

    @Inject
    CommonModelListPresenter mPresenter;

    private CommonModelListRecyclerAdapter mAdapter;

    private static final String EXTRA_USER_NAME = "extra_user_name";
    private static final String ACTION_DUBBLES = "com.anly.githubapp.ACTION_REPOS";

    public static void launchToShowDubbles(Context context, Integer userid) {
        Intent intent = new Intent(context, CommonModelListActivity.class);
        intent.putExtra(EXTRA_USER_NAME, userid);
        intent.setAction(ACTION_DUBBLES);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent();

        setContentView(R.layout.activity_common_model_list);

        ButterKnife.bind(this);

//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        mPresenter.attachView(this);
        loadData();

        mActionBar = this.getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setTitle("CommonModel");
        }
    }

    private void loadData() {
        String action = getIntent().getAction();

        Integer userid = getIntent().getIntExtra(EXTRA_USER_NAME, -1);

        if (ACTION_DUBBLES.equals(action)) {
            mPresenter.loadCommonModels(userid, CommonModelApi.DUBBLES);
        }
//        else if (ACTION_STARRED_REPOS.equals(action)) {
//            setTitle(isSelf ? getString(R.string.my_stars) : getString(R.string.your_stars, username));
//            mPresenter.loadRepos(username, isSelf, RepoApi.STARRED_REPOS);
//        }
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
        mPresenter.detachView();
    }

    private void initViews() {
        mAdapter = new CommonModelListRecyclerAdapter(null);
        mAdapter.setOnRecyclerViewItemClickListener(mItemtClickListener);

        mRepoListView.setLayoutManager(new LinearLayoutManager(this));
        mRepoListView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .color(Color.TRANSPARENT)
                .size(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .build());

        mRepoListView.setAdapter(mAdapter);
    }

    private BaseQuickAdapter.OnRecyclerViewItemClickListener mItemtClickListener = new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            CommonModel item = mAdapter.getItem(position);
//            RepoDetailActivity.launch(RepoListActivity.this, repo.getOwner().getLogin(), repo.getName());
        }
    };

    @Override
    public String getLoadingMessage() {
        return getString(R.string.loading);
    }

    @Override
    public void showContent(ArrayList<CommonModel> data) {
        mAdapter.setNewData(data);
    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public CommonModelComponent getComponent() {

        CommonModelComponent component = DaggerCommonModelComponent.builder()
                .applicationComponent(App.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .commonModelModule(new CommonModelModule())
                .build();
        component.inject(this);
        return component;
    }
}
