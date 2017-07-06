package live.itrip.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.adapter.ViewPagerAdapter;
import live.itrip.app.data.api.MessageApi;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.DaggerMainComponent;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.ui.base.BaseActivity;
import live.itrip.app.ui.fragment.common.MessageFragment;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/6/27.
 */

public class UserMessageActivity extends BaseActivity implements HasComponent<MainComponent> {

    @BindView(R.id.vp_horizontal_ntb)
    ViewPager mViewPager;

    private ActionBar mActionBar;
    private ViewPagerAdapter mViewPagerAdapter;
    private FragmentManager mFragmentManager = getSupportFragmentManager();


    public static void launch(Context context) {
        Intent intent = new Intent(context, UserMessageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getComponent().inject(this);
        setContentView(R.layout.activity_user_message);
        ButterKnife.bind(this);

        initViews();
//        mRecyclerViewPresenter.attachView(this);
    }

    private void initViews() {
        mActionBar = this.getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setTitle("我的消息");
        }

        mViewPagerAdapter = new ViewPagerAdapter(mFragmentManager);
        mViewPagerAdapter.addFragment(MessageFragment.newInstance(MessageApi.FLAG_SYSTEM));
        mViewPagerAdapter.addFragment(MessageFragment.newInstance(MessageApi.FLAG_USER));

        mViewPager.setAdapter(mViewPagerAdapter);

        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        models.add(
                new NavigationTabBar.Model.Builder(null,
                        Color.parseColor(colors[4]))
                        .title("私信")
                        .badgeTitle("私信")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(null,
                        Color.parseColor(colors[2]))
                        .title("系统消息")
                        .badgeTitle("系统消息")
                        .build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(mViewPager, 0);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                final NavigationTabBar.Model model = navigationTabBar.getModels().get(position);
//                navPage.hideBadge();
                AppLog.d("selected page:" + model.getTitle());
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

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
