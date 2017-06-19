package live.itrip.app.ui.activity;

import android.os.Bundle;

import live.itrip.app.data.PreferenceData;
import live.itrip.app.ui.base.BaseActivity;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/4/25.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppLog.d("trace===SplashActivity onCreate");
        if (PreferenceData.isFirstRunning(this)) {
            IntroduceActivity.launch(this);
        } else {
            MainActivity.launch(this);
        }
        finish();
    }
}
