package live.itrip.app.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import live.itrip.app.R;
import live.itrip.app.cache.DataCacheManager;
import live.itrip.app.cache.SharePreferenceData;
import live.itrip.app.data.model.UserModel;
import live.itrip.app.ui.activity.profile.FootmarksActivity;
import live.itrip.app.ui.activity.profile.UserMessageActivity;
import live.itrip.app.ui.activity.ImageCropActivity;
import live.itrip.app.ui.activity.profile.RecyclerViewActivity;
import live.itrip.app.ui.base.BaseFragment;
import live.itrip.app.ui.fragment.profile.UserInforDetailFragment;
import live.itrip.app.util.UIUtils;
import live.itrip.app.ui.view.dialog.QRCodeDialog;
import live.itrip.app.ui.widget.SelectPicturePopupWindow;
import live.itrip.common.crop.UCrop;
import live.itrip.common.util.AppLog;
import live.itrip.common.widget.PortraitView;
import live.itrip.common.widget.SolarSystemView;

/**
 * Created by Feng on 2017/4/25.
 */

public class ProfileFragment extends BaseFragment implements SelectPicturePopupWindow.OnSelectedListener {
    @BindView(R.id.iv_logo_setting)
    ImageView mIvLogoSetting;
    @BindView(R.id.iv_logo_zxing)
    ImageView mIvLogoZxing;
    @BindView(R.id.user_info_head_container)
    FrameLayout mFlUserInfoHeadContainer;
    @BindView(R.id.image_view_avatar)
    PortraitView mImageViewAvatar;
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
    @BindView(R.id.tv_bubbles)
    TextView mTvCount;
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

    private static final int GALLERY_REQUEST_CODE = 0;    // 相册选图标记
    private static final int CAMERA_REQUEST_CODE = 1;    // 相机拍照标记
    // 拍照临时图片
    private String mTempPhotoPath;
    // 剪切后图像文件
    private Uri mDestinationUri;
    /**
     * 选择提示 PopupWindow
     */
    private SelectPicturePopupWindow mSelectPicturePopupWindow;
    /**
     * 图片选择的监听回调
     */
    private OnPictureSelectedListener mOnPictureSelectedListener;

    private int mMaxRadius;
    private int mR;
    private float mPx;
    private float mPy;

    public static ProfileFragment newInstance() {
//        Bundle args = new Bundle();
//        args.putString("msg", text);
//        ProfileFragment fragment = new ProfileFragment();
//        fragment.setArguments(args);
        return new ProfileFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppLog.d("ProfileFragment ===> onCreateView");
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, root);

        // init ui
        initWidget(root);

        return root;
    }


    protected void initWidget(View root) {
        if (mFansView != null)
            mFansView.setVisibility(View.INVISIBLE);

        initSolar(root);
    }


    @OnClick({
            R.id.iv_logo_setting, R.id.iv_logo_zxing, R.id.image_view_avatar,
            R.id.user_view_solar_system, R.id.ly_bubbles, R.id.ly_favorite,
            R.id.ly_following, R.id.ly_follower, R.id.rl_message, R.id.rl_blog,
            R.id.rl_info_footmarks
    })
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.iv_logo_setting) {
            UIUtils.showSetting(getActivity());
            AppLog.e("setting click:");
        } else {
            switch (id) {
                case R.id.iv_logo_zxing:
                    QRCodeDialog dialog = new QRCodeDialog(getActivity());
                    dialog.show();
                    break;
                case R.id.image_view_avatar:
                    showAvatarOperation();
                    //查看头像 or 更换头像
                    break;
                case R.id.user_view_solar_system:
                    //显示我的资料
                    if (!SharePreferenceData.Account.checkLogon(this.getContext())) {
                        return;
                    }
                    UserModel mUserInfo = SharePreferenceData.Account.getLogonUser(this.getContext());
                    if (mUserInfo != null) {
                        Bundle userBundle = new Bundle();
                        userBundle.putSerializable(UserInforDetailFragment.EXTRA_USER_INFO, mUserInfo);
                        UIUtils.showUserInfo(getActivity(), userBundle);
                    }
                    break;
                case R.id.ly_bubbles:
                    RecyclerViewActivity.launchToShowBubbles(getActivity());
                    break;
                case R.id.ly_favorite:
                    RecyclerViewActivity.launchToShowFavorite(getActivity());
                    break;
                case R.id.ly_following:
                    RecyclerViewActivity.launchToShowFollowing(getActivity());
                    break;
                case R.id.ly_follower:
                    RecyclerViewActivity.launchToShowFollower(getActivity());
                    break;
                case R.id.rl_message:
                    UserMessageActivity.launch(getActivity());
                    break;
                case R.id.rl_blog:
                    RecyclerViewActivity.launchToShowBlogs(getActivity());
                    break;
                case R.id.rl_info_footmarks:
                    FootmarksActivity.launch(getActivity());
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * init solar view
     */
    private void initSolar(View root) {
        if (root != null) {
            root.post(new Runnable() {
                @Override
                public void run() {

                    if (mRlShowInfo == null) return;
                    int width = mRlShowInfo.getWidth();
                    float rlShowInfoX = mRlShowInfo.getX();

                    int height = mFlUserInfoIconContainer.getHeight();
                    float y1 = mFlUserInfoIconContainer.getY();

                    float x = mImageViewAvatar.getX();
                    float y = mImageViewAvatar.getY();
                    int portraitWidth = mImageViewAvatar.getWidth();

                    mPx = x + +rlShowInfoX + (width >> 1);
                    mPy = y1 + y + (height - y) / 2;
                    mMaxRadius = (int) (mSolarSystem.getHeight() - mPy + 250);
                    mR = (portraitWidth >> 1);

                    updateSolar(mPx, mPy);

                }
            });
        }
    }

    /**
     * update solar
     *
     * @param px float
     * @param py float
     */
    private void updateSolar(float px, float py) {

        SolarSystemView solarSystemView = mSolarSystem;
        Random random = new Random(System.currentTimeMillis());
        int maxRadius = mMaxRadius;
        int r = mR;
        solarSystemView.clear();
        for (int i = 40, radius = r + i; radius <= maxRadius; i = (int) (i * 1.4), radius += i) {
            SolarSystemView.Planet planet = new SolarSystemView.Planet();
            planet.setClockwise(random.nextInt(10) % 2 == 0);
            planet.setAngleRate((random.nextInt(35) + 1) / 1000.f);
            planet.setRadius(radius);
            solarSystemView.addPlanets(planet);

        }
        solarSystemView.setPivotPoint(px, py);
    }


    /**
     * 更换头像 or 查看头像
     */
    private void showAvatarOperation() {
        // 设置裁剪图片结果监听
        setOnPictureSelectedListener(new OnPictureSelectedListener() {
            @Override
            public void onPictureSelected(Uri fileUri, Bitmap bitmap) {
                mImageViewAvatar.setImageBitmap(bitmap);

                String filePath = fileUri.getEncodedPath();
                String imagePath = Uri.decode(filePath);
                Toast.makeText(getActivity(), "图片已经保存到:" + imagePath, Toast.LENGTH_LONG).show();
            }
        });

        mDestinationUri = Uri.fromFile(new File(DataCacheManager.getCacheDir(getContext()), "cropImage.jpeg"));
        mTempPhotoPath = DataCacheManager.getExternalStorageDirectory() + File.separator + "photo.jpeg";

        mSelectPicturePopupWindow = new SelectPicturePopupWindow(getActivity());
        mSelectPicturePopupWindow.setOnSelectedListener(this);

        mSelectPicturePopupWindow.showPopupWindow(getActivity());
    }


    @Override
    public void OnSelected(View v, int position) {
        switch (position) {
            case 0:
                // "拍照"按钮被点击了
                takePhoto();
                break;
            case 1:
                // "从相册选择"按钮被点击了
                pickFromGallery();
                break;
            case 2:
                // "取消"按钮被点击了
                mSelectPicturePopupWindow.dismissPopupWindow();
                break;
        }
    }

    private void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
//            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    getString(R.string.permission_write_storage_rationale),
//                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        } else {
            mSelectPicturePopupWindow.dismissPopupWindow();
            Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //下面这句指定调用相机拍照后的照片存储的路径
            takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mTempPhotoPath)));
            startActivityForResult(takeIntent, CAMERA_REQUEST_CODE);
        }
    }

    private void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
//            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
//                    getString(R.string.permission_read_storage_rationale),
//                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            mSelectPicturePopupWindow.dismissPopupWindow();
            Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
            // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:   // 调用相机拍照
                    File temp = new File(mTempPhotoPath);
                    startCropActivity(Uri.fromFile(temp));
                    break;
                case GALLERY_REQUEST_CODE:  // 直接从相册获取
                    startCropActivity(data.getData());
                    break;
                case UCrop.REQUEST_CROP:    // 裁剪图片结果
                    handleCropResult(data);
                    break;
                case UCrop.RESULT_ERROR:    // 裁剪图片错误
                    handleCropError(data);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startCropActivity(Uri uri) {
        UCrop.of(uri, mDestinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(512, 512)
                .withTargetActivity(ImageCropActivity.class)
                .start(getActivity(), this);
    }

    /**
     * 处理剪切成功的返回值
     *
     * @param result
     */
    private void handleCropResult(Intent result) {
        deleteTempPhotoFile();
        final Uri resultUri = UCrop.getOutput(result);
        if (null != resultUri && null != mOnPictureSelectedListener) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mOnPictureSelectedListener.onPictureSelected(resultUri, bitmap);
        } else {
            Toast.makeText(getActivity(), "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 处理剪切失败的返回值
     *
     * @param result
     */
    private void handleCropError(Intent result) {
        deleteTempPhotoFile();
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            AppLog.e("handleCropError: ", cropError);
            Toast.makeText(getActivity(), cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 删除拍照临时文件
     */
    private void deleteTempPhotoFile() {
        File tempFile = new File(mTempPhotoPath);
        if (tempFile.exists() && tempFile.isFile()) {
            tempFile.delete();
        }
    }

    /**
     * 设置图片选择的回调监听
     *
     * @param l
     */
    public void setOnPictureSelectedListener(OnPictureSelectedListener l) {
        this.mOnPictureSelectedListener = l;
    }

    /**
     * 图片选择的回调接口
     */
    public interface OnPictureSelectedListener {
        /**
         * 图片选择的监听回调
         *
         * @param fileUri
         * @param bitmap
         */
        void onPictureSelected(Uri fileUri, Bitmap bitmap);
    }
}
