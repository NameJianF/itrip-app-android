package live.itrip.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.adapter.MessageRecyclerAdapter;
import live.itrip.app.data.model.MessageModel;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.DaggerMessageDetailComponent;
import live.itrip.app.di.component.MessageDetailComponent;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.di.module.MessageModule;
import live.itrip.app.presenter.MessagePresenter;
import live.itrip.app.ui.base.BaseLoadingActivity;
import live.itrip.common.mvp.view.LceView;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/7/5.
 */

public class DialogMessageActivity extends BaseLoadingActivity implements LceView<ArrayList<MessageModel>>, HasComponent<MessageDetailComponent> {

    private ActionBar mActionBar;
    @BindView(R.id.root)
    CoordinatorLayout mCoordinatorLayout;
//    @BindView(R.id.refreshLayout)
//    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @Inject
    MessagePresenter mMessagePresenter;
    private MessageRecyclerAdapter mMessageRecyclerAdapter;

    private static final String EXTRA_FROM_USER_ID = "extra_from_user_id";
    private static final String EXTRA_TO_USER_ID = "extra_to_user_id";

    public static void launch(Context context, Long fromUserId, Long toUserId) {
        Intent intent = new Intent(context, DialogMessageActivity.class);
        intent.putExtra(EXTRA_FROM_USER_ID, fromUserId);
        intent.putExtra(EXTRA_TO_USER_ID, toUserId);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setContentView(R.layout.activity_dialog_message);
        ButterKnife.bind(this);

        initViews();
        mMessagePresenter.attachView(this);
        loadData();
    }

    private void initViews() {
        mActionBar = this.getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setTitle("");
        }

        mMessageRecyclerAdapter = new MessageRecyclerAdapter(null);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(this)
                .color(Color.TRANSPARENT)
                .size(getResources().getDimensionPixelSize(R.dimen.divider_height))
                .build());

        mRecyclerView.setAdapter(mMessageRecyclerAdapter);
    }

    private void loadData() {
        Long fromUserId = getIntent().getLongExtra(EXTRA_FROM_USER_ID, 0L);
        Long toUserId = getIntent().getLongExtra(EXTRA_TO_USER_ID, 0L);

        this.mMessagePresenter.loadDialogMesages(fromUserId, toUserId, 0L);
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
        mMessagePresenter.detachView();
    }

    @Override
    public String getLoadingMessage() {
        return getString(R.string.loading);
    }

    @Override
    public void showContent(ArrayList<MessageModel> data) {
        mMessageRecyclerAdapter.setNewData(data);
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
        AppLog.d("trace===DialogMessageActivity onResume");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public MessageDetailComponent getComponent() {
        return DaggerMessageDetailComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .activityModule(new ActivityModule(this))
                .messageModule(new MessageModule())
                .build();
    }
}
