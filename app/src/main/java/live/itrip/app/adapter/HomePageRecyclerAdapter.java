package live.itrip.app.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import live.itrip.app.R;
import live.itrip.app.data.model.HomePageModel;
import live.itrip.common.util.StringUtils;

/**
 * Created by Feng on 2017/7/12.
 */

public class HomePageRecyclerAdapter extends BaseQuickAdapter<HomePageModel> {
    public HomePageRecyclerAdapter(List<HomePageModel> data) {
        super(R.layout.fragment_home_category, data);
    }

    @Override
    protected void convert(final BaseViewHolder holder, HomePageModel homePageModel) {
        Picasso.with(this.mContext)
                .load(homePageModel.getImgUrl())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        holder.setImageBitmap(R.id.category_img, bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
        holder.setText(R.id.category_title_tv, StringUtils.replaceAllBlank(homePageModel.getTitle()));
        holder.setText(R.id.category_sub_tv, StringUtils.trimNewLine(homePageModel.getContent()));
    }
}
