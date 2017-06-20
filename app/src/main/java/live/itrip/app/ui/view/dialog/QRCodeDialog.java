package live.itrip.app.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import java.io.File;

import live.itrip.app.R;
import live.itrip.app.data.PreferenceData;
import live.itrip.app.ui.util.ImageUtils;
import live.itrip.app.ui.util.ToastUtils;
import live.itrip.common.util.QrCodeUtils;

/**
 * Created by 建锋 on 2017/6/20.
 */
public class QRCodeDialog extends Dialog {

    private ImageView mIvCode;
    private Bitmap bitmap;

    public QRCodeDialog(Context context) {
        this(context, R.style.AppTheme);
    }

    public QRCodeDialog(@NonNull Context context, boolean flag,
                        OnCancelListener listener) {
        super(context, flag, listener);
    }

    private QRCodeDialog(final Context context, int defStyle) {
        super(context, defStyle);
        View contentView = getLayoutInflater().inflate(
                R.layout.dialog_qr_code, null);
        mIvCode = (ImageView) contentView.findViewById(R.id.iv_qr_code);
        try {
            bitmap = QrCodeUtils.Create2DCode(String.format(
                    "http://itrip.live/u/%s", PreferenceData.Account.getUserId()));
            mIvCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mIvCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dismiss();
                try {
                    String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    File file = new File(sdPath + File.separator + "itrip"
                            + File.separator);
                    if (!file.exists()) file.mkdirs();
                    file = new File(file.getAbsoluteFile(), "qrcode.png");
                    if (file.exists()) file.delete();
                    ImageUtils.saveImageToSD(context, file.getAbsolutePath(), bitmap, 100);
                    ToastUtils.showToast("二维码已保存到itrip文件夹下");
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToast("SD卡不可写，二维码保存失败");
                }

                return false;
            }
        });

        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                QRCodeDialog.this.dismiss();
                return false;
            }
        });
        super.setContentView(contentView);
    }


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setGravity(Gravity.CENTER);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(p);
    }
}
