package live.itrip.app.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import live.itrip.app.App;
import live.itrip.app.di.component.ActivityComponent;
import live.itrip.app.di.component.DaggerActivityComponent;
import live.itrip.app.di.module.ActivityModule;

/**
 * Created by Feng on 2017/4/25.
 */

public class BaseActivity extends AppCompatActivity {
    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
