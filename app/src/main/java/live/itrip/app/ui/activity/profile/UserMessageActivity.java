package live.itrip.app.ui.activity.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.adapter.fragment.UserMsgFragmentAdapter;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.DaggerMainComponent;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.ui.base.BaseActivity;
import live.itrip.app.ui.fragment.profile.MessageFragment;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/6/27.
 */

public class UserMessageActivity extends BaseActivity implements HasComponent<MainComponent> {

    @BindView(R.id.tabs)
    PagerSlidingTabStrip mTabs;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private UserMsgFragmentAdapter mFragmentAdapter;

    private static final Integer[] FRAGMENT_CATEGORY = {
            MessageFragment.MESSAGE_SELF,
            MessageFragment.MESSAGE_SYSTEM
    };


    public static void launch(Context context) {
        Intent intent = new Intent(context, UserMessageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user_message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        this.setActionBarTitle("我的消息");

        mFragmentAdapter = new UserMsgFragmentAdapter(getSupportFragmentManager(), FRAGMENT_CATEGORY);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(FRAGMENT_CATEGORY.length);

        mTabs.setViewPager(mViewPager);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppLog.d("trace===FeedBackActivity onResume");
    }

    @Override
    public MainComponent getComponent() {
        return DaggerMainComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }
}
