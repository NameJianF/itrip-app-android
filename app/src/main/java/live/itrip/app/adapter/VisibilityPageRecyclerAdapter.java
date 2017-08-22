package live.itrip.app.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import live.itrip.app.R;
import live.itrip.app.data.model.ChildMultiItem;
import live.itrip.app.data.model.VisibilityPageModel;
import live.itrip.app.util.ToastUtils;

/**
 * Created by Feng on 2017/7/12.
 */

public class VisibilityPageRecyclerAdapter extends BaseMultiItemQuickAdapter<VisibilityPageModel> {


    public VisibilityPageRecyclerAdapter(List<VisibilityPageModel> data) {
        super(data);
        addItemType(VisibilityPageModel.ITEM_NAV, R.layout.fragment_visi_nav);
        addItemType(VisibilityPageModel.ITEM_HOT, R.layout.fragment_visi_hot);
        addItemType(VisibilityPageModel.ITEM_CATEGORY, R.layout.fragment_visi_category);
        addItemType(VisibilityPageModel.ITEM_BLOG, R.layout.fragment_visi_blog);
        addItemType(VisibilityPageModel.ITEM_AD, R.layout.fragment_home_ad);
    }

    @Override
    protected void convert(BaseViewHolder holder, VisibilityPageModel model) {
        switch (holder.getItemViewType()) {
            case VisibilityPageModel.ITEM_NAV:
                initViewNav(holder, model);
                break;
            case VisibilityPageModel.ITEM_HOT:
                initViewHot(holder, model);
                break;
            case VisibilityPageModel.ITEM_CATEGORY:
                initViewCategory(holder, model);
                break;
            case VisibilityPageModel.ITEM_BLOG:
                initViewBlog(holder, model);
                break;
            case VisibilityPageModel.ITEM_AD:
                initViewAd(holder, model);
                break;
        }

    }

    private void initViewNav(BaseViewHolder holder, VisibilityPageModel model) {
        holder.getView(R.id.relId1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("景点 clicked.");
            }
        });
        holder.getView(R.id.relId2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("美食 clicked.");
            }
        });
        holder.getView(R.id.relId3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("目的地 clicked.");
            }
        });
        holder.getView(R.id.relId4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("购物 clicked.");
            }
        });
    }

    private void initViewHot(final BaseViewHolder holder, VisibilityPageModel model) {
        Picasso.with(this.mContext)
                .load(model.getImgUrl())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into((ImageView) holder.getView(R.id.hot_img));

        holder.getView(R.id.linearLayout_hot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("Hot clicked.");
            }
        });
    }

    private void initViewCategory(BaseViewHolder holder, VisibilityPageModel model) {
        if (model.getItems() != null) {
            RecyclerView mRecyclerView = holder.getView(R.id.rv_list);
            final ArrayList<ChildMultiItem> data = model.getItems();
            final MultipleItemQuickAdapter multipleItemAdapter = new MultipleItemQuickAdapter(data);
            final GridLayoutManager manager = new GridLayoutManager(this.mContext, 2);
            mRecyclerView.setLayoutManager(manager);

            multipleItemAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {
                    ToastUtils.showToast("Hot clicked : " + i);
                }
            });

            mRecyclerView.setAdapter(multipleItemAdapter);

        }
    }

    private void initViewBlog(BaseViewHolder holder, VisibilityPageModel model) {
        if (model.getItems() != null) {
            RecyclerView mRecyclerView = holder.getView(R.id.rv_list);
            final ArrayList<ChildMultiItem> data = model.getItems();
            final MultipleItemQuickAdapter multipleItemAdapter = new MultipleItemQuickAdapter(data);
            final GridLayoutManager manager = new GridLayoutManager(this.mContext, 2);
            mRecyclerView.setLayoutManager(manager);

            multipleItemAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {
                    ToastUtils.showToast("Hot clicked : " + i);
                }
            });

            mRecyclerView.setAdapter(multipleItemAdapter);
        }
    }

    private void initViewAd(BaseViewHolder holder, VisibilityPageModel model) {
        ImageView imageView = holder.getView(R.id.ad_img);
        Picasso.with(this.mContext)
                .load(model.getImgUrl())
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.place_holder)
                .into(imageView);
        // image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("ad image clicked.");
            }
        });
    }

}
