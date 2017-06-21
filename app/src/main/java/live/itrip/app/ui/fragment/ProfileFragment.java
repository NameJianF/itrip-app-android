package live.itrip.app.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import live.itrip.app.R;
import live.itrip.app.data.PreferenceData;
import live.itrip.app.ui.activity.ImageCropActivity;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.app.ui.util.UIUtils;
import live.itrip.app.ui.view.dialog.QRCodeDialog;
import live.itrip.common.crop.UCrop;
import live.itrip.common.util.AppLog;
import live.itrip.common.widget.PortraitView;
import live.itrip.common.widget.SolarSystemView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Feng on 2017/4/25.
 */

public class ProfileFragment extends BaseFragment {
    private static final int REQUEST_SELECT_PICTURE = 0x01;

    @BindView(R.id.iv_logo_setting)
    ImageView mIvLogoSetting;
    @BindView(R.id.iv_logo_zxing)
    ImageView mIvLogoZxing;
    @BindView(R.id.user_info_head_container)
    FrameLayout mFlUserInfoHeadContainer;
    @BindView(R.id.iv_portrait)
    PortraitView mPortrait;
    @BindView(R.id.iv_gender)
    ImageView mIvGander;
    @BindView(R.id.user_info_icon_container)
    FrameLayout mFlUserInfoIconContainer;
    @BindView(R.id.tv_nick)
    TextView mTvName;
    @BindView(R.id.tv_score)
    TextView mTvScore;
    @BindView(R.id.user_view_solar_system)
    SolarSystemView mSolarSystem;
    @BindView(R.id.rl_show_my_info)
    LinearLayout mRlShowInfo;
    @BindView(R.id.about_line)
    View mAboutLine;
    @BindView(R.id.lay_about_info)
    LinearLayout mLayAboutCount;
    @BindView(R.id.tv_tweet)
    TextView mTvTweetCount;
    @BindView(R.id.tv_favorite)
    TextView mTvFavoriteCount;
    @BindView(R.id.tv_following)
    TextView mTvFollowCount;
    @BindView(R.id.tv_follower)
    TextView mTvFollowerCount;
    @BindView(R.id.user_info_notice_message)
    TextView mMesView;
    @BindView(R.id.user_info_notice_fans)
    TextView mFansView;

    public static ProfileFragment newInstance() {
//        Bundle args = new Bundle();
//        args.putString("msg", text);
        ProfileFragment fragment = new ProfileFragment();
//        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppLog.d("ProfileFragment ===> onCreateView");
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, root);

        mDestinationUri = Uri.fromFile(new File(getActivity().getCacheDir(), "cropImage.jpeg"));

        return root;
    }

    @OnClick({
            R.id.iv_logo_setting, R.id.iv_logo_zxing, R.id.iv_portrait,
            R.id.user_view_solar_system, R.id.ly_tweet, R.id.ly_favorite,
            R.id.ly_following, R.id.ly_follower, R.id.rl_message, R.id.rl_blog,
            R.id.rl_info_question
    })
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.iv_logo_setting) {
            UIUtils.showSetting(getActivity());
            AppLog.e("setting click:");
        } else {
//            if (!PreferenceData.Account.checkLogon(this.getActivity())) {
//                return;
//            }

            switch (id) {
                case R.id.iv_logo_zxing:
                    QRCodeDialog dialog = new QRCodeDialog(getActivity());
                    dialog.show();
                    break;
                case R.id.iv_portrait:
                    showAvatarOperation();
                    //查看头像 or 更换头像
                    break;
                case R.id.user_view_solar_system:
                    //显示我的资料
                    break;
                case R.id.ly_tweet:
                    break;
                case R.id.ly_favorite:
                    break;
                case R.id.ly_following:
                    break;
                case R.id.ly_follower:
                    break;
                case R.id.rl_message:
                    break;
                case R.id.rl_blog:
                    break;
                case R.id.rl_info_question:
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 更换头像 or 查看头像
     */
    private void showAvatarOperation() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_SELECT_PICTURE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(data.getData());
                } else {
                    Toast.makeText(getContext(), "无法剪切选择图片", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
    }

    // 剪切后图像文件
    private Uri mDestinationUri;

    /**
     * 开始剪切图片
     *
     * @param uri
     */
    private void startCropActivity(Uri uri) {
//        ImageCropActivity.launch(getContext());

        UCrop.of(uri, mDestinationUri)
                .withTargetActivity(ImageCropActivity.class)
                .withAspectRatio(1, 1)
//                .withMaxResultSize(500, 500)
                .start(getActivity());

    }

    /**
     * 处理剪切后的返回值
     *
     * @param result
     */
    private void handleCropResult(Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
//            imageIv.setImageURI(resultUri);
            Bitmap bmp;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                mPortrait.setImageBitmap(bmp);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        } else {
            Toast.makeText(getContext(), "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }
}
