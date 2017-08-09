package live.itrip.app.ui.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import live.itrip.app.App;
import live.itrip.app.R;
import live.itrip.app.di.component.ActivityComponent;
import live.itrip.app.di.component.DaggerActivityComponent;
import live.itrip.app.di.module.ActivityModule;

/**
 * Created by Feng on 2017/4/25.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private ActivityComponent mActivityComponent;
    private TextView mToolbarTitle;
    protected Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        // set toolbar
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolBar != null) {
            mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
            setSupportActionBar(mToolBar);
            ActionBar mActionBar = this.getSupportActionBar();
            if (mActionBar != null) {
                mActionBar.setDisplayHomeAsUpEnabled(true);
                mActionBar.setDisplayShowHomeEnabled(true);
                mActionBar.setDisplayShowTitleEnabled(false);
//            mActionBar.setHomeButtonEnabled(false);
            }
        }
    }

    /**
     * @return
     */
    protected abstract int getContentView();

    public void setToolBarShow(boolean show) {
        if (show) {
            this.mToolBar.setVisibility(View.VISIBLE);
        } else {
            this.mToolBar.setVisibility(View.GONE);
        }
    }

    public void setActionBarTitle(String actionBarTitle) {
        if (this.mToolbarTitle != null) {
            this.mToolbarTitle.setText(actionBarTitle);
        }
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(App.getInstance().getComponent())
                    .build();
        }
        return mActivityComponent;
    }

}
