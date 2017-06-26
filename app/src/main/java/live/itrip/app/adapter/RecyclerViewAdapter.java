package live.itrip.app.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import live.itrip.app.R;
import live.itrip.app.data.model.RecyclerViewItem;
import live.itrip.common.util.StringUtils;

/**
 * Created by Feng on 2017/6/26.
 */

public class RecyclerViewAdapter extends BaseQuickAdapter<RecyclerViewItem> {

    public RecyclerViewAdapter(List<RecyclerViewItem> data) {
        super(R.layout.item_recycler_model, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, RecyclerViewItem recyclerViewItem) {
        baseViewHolder.setText(R.id.title, StringUtils.replaceAllBlank(recyclerViewItem.getTitle()));
        baseViewHolder.setText(R.id.desc, StringUtils.trimNewLine(recyclerViewItem.getDesc()));
    }
}
