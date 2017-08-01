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
        addItemType(ChildMultiItem.ITEM_PLAN, R.layout.item_plan_view);
        addItemType(ChildMultiItem.ITEM_BOLG, R.layout.item_blog_view);
//        addItemType(ChildMultiItem.IMG_TEXT, R.layout.item_plan_view);
    }

    @Override
    protected void convert(BaseViewHolder holder, ChildMultiItem bean) {
        try {
            switch (holder.getItemViewType()) {
                case ChildMultiItem.ITEM_PLAN:
                    initViewItemPlan(holder, bean);
                    break;
                case ChildMultiItem.ITEM_BOLG:
                    initViewItemBlog(holder, bean);
                    break;
//                case ChildMultiItem.IMG_TEXT:
//                    initViewImageText(holder, bean);
//                    break;
            }
        } catch (Exception e) {
            AppLog.e(e);
        }

    }

    private void initViewItemPlan(BaseViewHolder holder, ChildMultiItem bean) {
        // image
        Picasso.with(this.mContext)
                .load(bean.getImageUrl())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into((ImageView) holder.getView(R.id.iv));
        // title
        holder.setText(R.id.tvTitle, StringUtils.trimNewLine(bean.getTitle()));
        // price
        holder.setText(R.id.tvPlanPrice, StringUtils.trimNewLine(String.format("¥ %s", bean.getPrice())));
        // participate  参加
        holder.setText(R.id.tvParticipate, StringUtils.trimNewLine(String.format("%s 参与", bean.getParticipate())));

    }

    private void initViewItemBlog(BaseViewHolder holder, ChildMultiItem bean) {
        // iamge
        Picasso.with(this.mContext)
                .load(bean.getImageUrl())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into((ImageView) holder.getView(R.id.iv));
        // title
        holder.setText(R.id.tv, StringUtils.trimNewLine(bean.getTitle()));
        //  author
        holder.setText(R.id.tvAuthor, StringUtils.trimNewLine(bean.getAuthor()));
        // to view
        holder.setText(R.id.tvToView, StringUtils.trimNewLine(bean.getToView().toString()));
        // favorite
        holder.setText(R.id.tvFavorite, StringUtils.trimNewLine(bean.getFavorite().toString()));
    }

    private void initViewImageText(BaseViewHolder holder, ChildMultiItem bean) {

    }


}
