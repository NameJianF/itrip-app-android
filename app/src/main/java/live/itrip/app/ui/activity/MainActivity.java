package live.itrip.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.adapter.fragment.ViewPagerAdapter;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.DaggerMainComponent;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.ui.base.BaseActivity;
import live.itrip.app.ui.fragment.HomeFragment;
import live.itrip.app.ui.fragment.ProfileFragment;
import live.itrip.app.ui.fragment.TravelFragment;
import live.itrip.app.ui.fragment.VisibilityFragment;
import live.itrip.common.navigation.SpaceItem;
import live.itrip.common.navigation.SpaceNavigationView;
import live.itrip.common.navigation.SpaceOnClickListener;
import live.itrip.common.navigation.SpaceOnLongClickListener;
import live.itrip.common.util.AppLog;

public class MainActivity extends BaseActivity implements HasComponent<MainComponent> {

    @BindView(R.id.vp_horizontal_ntb)
    ViewPager mViewPager;
    @BindView(R.id.space)
    SpaceNavigationView mSpaceNavigationView;

    private ViewPagerAdapter mViewPagerAdapter;
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private ActionBar mActionBar;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mSpaceNavigationView.onSaveInstanceState(outState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mSpaceNavigationView.initWithSaveInstanceState(savedInstanceState);

        initViews();

        AppLog.d("trace===MainActivity onCreate, end");
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppLog.d("trace===MainActivity onResume");
    }

    private void initViews() {
        mActionBar = this.getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.hide();
        }


        mViewPagerAdapter = new ViewPagerAdapter(mFragmentManager);
        mViewPagerAdapter.addFragment(HomeFragment.newInstance()); // 首页
        mViewPagerAdapter.addFragment(VisibilityFragment.newInstance()); // 发现
        mViewPagerAdapter.addFragment(TravelFragment.newInstance()); // 行程
        mViewPagerAdapter.addFragment(ProfileFragment.newInstance()); // 我的
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mSpaceNavigationView.changeCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mSpaceNavigationView.addSpaceItem(new SpaceItem("首页", R.drawable.home));
        mSpaceNavigationView.addSpaceItem(new SpaceItem("发现", R.drawable.heart));
        mSpaceNavigationView.addSpaceItem(new SpaceItem("行程", R.drawable.near_me));
        mSpaceNavigationView.addSpaceItem(new SpaceItem("我的", R.drawable.account));

//        mSpaceNavigationView.shouldShowFullBadgeText(true);
//        mSpaceNavigationView.setCentreButtonIconColorFilterEnabled(false);

        mSpaceNavigationView.setCentreButtonIcon(R.drawable.camera);
//        mSpaceNavigationView.showIconOnly();

        mSpaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                AppLog.d("onCentreButtonClick ", "onCentreButtonClick");
//                mSpaceNavigationView.shouldShowFullBadgeText(true);

            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                AppLog.d("onItemClick ", "" + itemIndex + " " + itemName);
                mViewPager.setCurrentItem(itemIndex);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                AppLog.d("onItemReselected ", "" + itemIndex + " " + itemName);
            }
        });

        mSpaceNavigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
            @Override
            public void onCentreButtonLongClick() {
                Toast.makeText(MainActivity.this, "onCentreButtonLongClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int itemIndex, String itemName) {
                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private long mLastBackTime = 0;

    @Override
    public void onBackPressed() {
        // finish while click back key 2 times during 1s.
        if ((System.currentTimeMillis() - mLastBackTime) < 1000) {
            finish();
        } else {
            mLastBackTime = System.currentTimeMillis();
            Toast.makeText(this, R.string.exit_click_back_again, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public MainComponent getComponent() {
        return DaggerMainComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }
}