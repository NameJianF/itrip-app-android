package live.itrip.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.adapter.ViewPagerAdapter;
import live.itrip.app.di.HasComponent;
import live.itrip.app.di.component.DaggerMainComponent;
import live.itrip.app.di.component.MainComponent;
import live.itrip.app.di.module.ActivityModule;
import live.itrip.app.ui.SimpleBackPage;
import live.itrip.app.ui.base.BaseActivity;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/6/20.
 */

public class SimpleBackActivity extends BaseActivity implements HasComponent<MainComponent> {
    @BindView(R.id.vp_horizontal_ntb)
    ViewPager mViewPager;

    private ViewPagerAdapter mViewPagerAdapter;
    private FragmentManager mFragmentManager = getSupportFragmentManager();

    public final static String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";
    public final static String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";
    protected int mPageValue = -1;

    @Override
    protected int getContentView() {
        return R.layout.activity_simple_fragment;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (mPageValue == -1) {
            mPageValue = intent.getIntExtra(BUNDLE_KEY_PAGE, 0);
        }

        initViews(mPageValue, getIntent());


        AppLog.d("trace===SimpleBackActivity onCreate, end");
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppLog.d("trace===SimpleBackActivity onResume");
    }

    protected void initViews(int pageValue, Intent data) {
        if (data == null) {
            throw new RuntimeException("you must provide a page info to display");
        }
        SimpleBackPage page = SimpleBackPage.getPageByValue(pageValue);
        if (page == null) {
            throw new IllegalArgumentException("can not find page by value:" + pageValue);
        }

        try {
            BaseFragment fragment = (BaseFragment) page.getClz().newInstance();

            mViewPagerAdapter = new ViewPagerAdapter(mFragmentManager);
            mViewPagerAdapter.addFragment(fragment);
            mViewPager.setAdapter(mViewPagerAdapter);

            Bundle args = data.getBundleExtra(BUNDLE_KEY_ARGS);
            if (args != null) {
                fragment.setArguments(args);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "generate fragment error. by value:" + pageValue);
        }
    }

    public void setToolBarTitle(String title){
        this.setActionBarTitle(title);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public MainComponent getComponent() {
        return DaggerMainComponent.builder()
                .applicationComponent(App.getInstance().getComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

}
