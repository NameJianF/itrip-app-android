package live.itrip.app.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import live.itrip.app.R;
import live.itrip.app.data.model.ChildMultiItem;
import live.itrip.common.util.AppLog;
import live.itrip.common.util.StringUtils;

/**
 * Created by Feng on 2017/7/14.
 */

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<ChildMultiItem> {

    public MultipleItemQuickAdapter(ArrayList<ChildMultiItem> data) {
        super(data);
        addItemType(ChildMultiItem.TEXT, R.layout.item_img_text_view);
        addItemType(ChildMultiItem.IMG, R.layout.item_img_text_view);
        addItemType(ChildMultiItem.IMG_TEXT, R.layout.item_img_text_view);
    }

    @Override
    protected void convert(BaseViewHolder holder, ChildMultiItem bean) {
        try {
            switch (holder.getItemViewType()) {
                case ChildMultiItem.TEXT:
                    initViewText(holder, bean);
                    break;
                case ChildMultiItem.IMG:
                    initViewImage(holder, bean);
                    break;
                case ChildMultiItem.IMG_TEXT:
                    initViewImageText(holder, bean);
                    break;
            }
        } catch (Exception e) {
            AppLog.e(e);
        }

    }

    private void initViewText(BaseViewHolder holder, ChildMultiItem bean) {

    }

    private void initViewImage(BaseViewHolder holder, ChildMultiItem bean) {

    }

    private void initViewImageText(BaseViewHolder holder, ChildMultiItem bean) {
        Picasso.with(this.mContext)
                .load(bean.getImageUrl())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into((ImageView) holder.getView(R.id.iv));
        holder.setText(R.id.tv, StringUtils.trimNewLine(bean.getTitle()));
    }


}
