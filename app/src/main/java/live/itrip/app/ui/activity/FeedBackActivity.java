package live.itrip.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import butterknife.ButterKnife;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.DaggerFeedBackComponent;
import live.itrip.app.di.component.FeedBackComponent;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.ui.base.BaseActivity;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/6/20.
 */

public class FeedBackActivity extends BaseActivity implements HasComponent<FeedBackComponent> {
    public static void launch(Context context) {
        context.startActivity(new Intent(context, FeedBackActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        initViews();

        AppLog.d("trace===FeedBackActivity onCreate, end");
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppLog.d("trace===FeedBackActivity onResume");
    }

    private void initViews() {
        this.setActionBarTitle(getString(R.string.feedback));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public FeedBackComponent getComponent() {
        return DaggerFeedBackComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }
}
