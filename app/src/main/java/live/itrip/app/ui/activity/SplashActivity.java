package live.itrip.app.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import live.itrip.app.cache.SharePreferenceData;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/4/25.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppLog.d("trace===SplashActivity onCreate");
        if (SharePreferenceData.isFirstRunning(this)) {
            IntroduceActivity.launch(this);
        } else {
            MainActivity.launch(this);
        }
        finish();
    }
}
