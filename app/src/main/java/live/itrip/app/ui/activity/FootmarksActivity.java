package live.itrip.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import butterknife.ButterKnife;
import live.itrip.app.R;
import live.itrip.app.ui.base.BaseActivity;
import live.itrip.common.util.AppLog;

/**
 * Created by Feng on 2017/6/27.
 */

public class FootmarksActivity extends BaseActivity {
    private ActionBar mActionBar;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, FootmarksActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footmarks);

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
        mActionBar = this.getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setTitle(R.string.my_travel);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
