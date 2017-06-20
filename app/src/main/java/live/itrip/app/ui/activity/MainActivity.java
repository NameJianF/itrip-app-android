package live.itrip.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.gigamole.navigationtabbar.ntb.NavigationTabBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.adapter.ViewPagerAdapter;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.DaggerMainComponent;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.ui.base.BaseActivity;
import live.itrip.app.ui.fragment.HomeFragment;
import live.itrip.app.ui.fragment.PositionFragment;
import live.itrip.app.ui.fragment.ProfileFragment;
import live.itrip.app.ui.fragment.TravelFragment;
import live.itrip.app.ui.fragment.VisibilityFragment;
import live.itrip.common.util.AppLog;

public class MainActivity extends BaseActivity implements HasComponent<MainComponent> {

    @BindView(R.id.vp_horizontal_ntb)
    ViewPager mViewPager;

    private ViewPagerAdapter mViewPagerAdapter;
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private ActionBar mToolbar;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initViews();

        AppLog.d("trace===MainActivity onCreate, end");
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppLog.d("trace===MainActivity onResume");
    }

    private void initViews() {
        mToolbar = this.getSupportActionBar();
        mToolbar.hide();


        mViewPagerAdapter = new ViewPagerAdapter(mFragmentManager);
        mViewPagerAdapter.addFragment(HomeFragment.newInstance());
        mViewPagerAdapter.addFragment(VisibilityFragment.newInstance());
        mViewPagerAdapter.addFragment(PositionFragment.newInstance());
        mViewPagerAdapter.addFragment(TravelFragment.newInstance());
        mViewPagerAdapter.addFragment(ProfileFragment.newInstance());
        mViewPager.setAdapter(mViewPagerAdapter);

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("首页")
//                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("发现")
//                        .badgeTitle("with")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fifth),
                        Color.parseColor(colors[4]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("位置")
//                        .badgeTitle("777")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("行程")
//                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("我的")
//                        .badgeTitle("icon")
                        .build()
        );


        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
//        navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.BOTTOM);
//        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.CENTER);
//        navigationTabBar.setTypeface("fonts/custom_font.ttf");
//        navigationTabBar.setIsBadged(true);
//        navigationTabBar.setIsTitled(true);
//        navigationTabBar.setIsTinted(true);
//        navigationTabBar.setIsBadgeUseTypeface(true);
//        navigationTabBar.setBadgeBgColor(Color.RED);
//        navigationTabBar.setBadgeTitleColor(Color.WHITE);
//        navigationTabBar.setIsSwiped(true);
//        navigationTabBar.setBgColor(Color.BLACK);
//        navigationTabBar.setBadgeSize(10);
//        navigationTabBar.setTitleSize(10);
//        navigationTabBar.setIconSizeFraction(0.5);

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

//        navigationTabBar.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
//                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
//                    navigationTabBar.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            model.showBadge();
//                        }
//                    }, i * 100);
//                }
//            }
//        }, 500);
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
                .applicationComponent(App.get(this).getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }
}