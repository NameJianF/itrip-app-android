package live.itrip.app.ui.activity.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import live.itrip.app.R;
import live.itrip.app.ui.base.BaseActivity;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/6/27.
 */

public class FootmarksActivity extends BaseActivity {
    public static void launch(Context context) {
        context.startActivity(new Intent(context, FootmarksActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_footmarks;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initViews();

        AppLog.d("trace===FootmarksActivity onCreate, end");
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppLog.d("trace===FootmarksActivity onResume");
    }

    private void initViews() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
