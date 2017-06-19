package live.itrip.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import live.itrip.app.R;
import live.itrip.app.data.PreferenceData;
import live.itrip.common.Indicator.AppIntro2;
import live.itrip.common.Indicator.AppIntroFragment;

/**
 * Created by Feng on 2017/4/25.
 *
 * 欢迎页
 */
public class IntroduceActivity extends AppIntro2 {

    public static void launch(Context context) {
        context.startActivity(new Intent(context, IntroduceActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(
                getString(R.string.title_1),
                getString(R.string.title_font),
                getString(R.string.desc_1),
                getString(R.string.desc_font),
                R.drawable.ic_slide1,
                getResources().getColor(R.color.md_indigo_400)
        ));

        addSlide(AppIntroFragment.newInstance(
                getString(R.string.title_2),
                getString(R.string.title_font),
                getString(R.string.desc_2),
                getString(R.string.desc_font),
                R.drawable.ic_slide2,
                getResources().getColor(R.color.md_cyan_500)
        ));

        addSlide(AppIntroFragment.newInstance(
                getString(R.string.title_3),
                getString(R.string.title_font),
                getString(R.string.desc_3),
                getString(R.string.desc_font),
                R.drawable.ic_slide3,
                getResources().getColor(R.color.md_green_500)
        ));
        addSlide(AppIntroFragment.newInstance(
                getString(R.string.title_3),
                getString(R.string.title_font),
                getString(R.string.desc_3),
                getString(R.string.desc_font),
                R.drawable.ic_slide4,
                getResources().getColor(R.color.md_green_500)
        ));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        goMain();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        goMain();
    }

    private void goMain() {
        PreferenceData.setAlreadyRun(this);

        MainActivity.launch(this);
        finish();
    }
}