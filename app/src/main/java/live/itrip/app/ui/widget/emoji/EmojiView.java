package live.itrip.app.ui.widget.emoji;

import android.content.Context;
import android.widget.EditText;

import live.itrip.app.ui.util.DeviceUtils;
import live.itrip.app.ui.util.InputHelper;
import live.itrip.app.ui.widget.face.FacePanelView;
import live.itrip.app.ui.widget.face.FaceRecyclerView;

/**
 * Created by Feng on 2017/7/26.
 */

public class EmojiView extends FacePanelView {
    private EditText mEditText;

    public EmojiView(Context context, EditText editText) {
        super(context);
        this.mEditText = editText;
        setListener(new FacePanelListener() {
            @Override
            public void onDeleteClick() {
                InputHelper.backspace(mEditText);
            }

            @Override
            public void hideSoftKeyboard() {
                DeviceUtils.hideSoftKeyboard(mEditText);
            }

            @Override
            public void onFaceClick(Emojicon v) {
                InputHelper.input2OSC(mEditText, v);
            }
        });
    }

    @Override
    protected FaceRecyclerView createRecyclerView() {
        return new EmojiRecyclerView(getContext(), this);
    }
}