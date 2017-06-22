package live.itrip.app.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import live.itrip.app.R;
import live.itrip.app.data.model.CommonModel;
import live.itrip.common.util.StringUtils;

/**
 * Created by Feng on 2017/6/22.
 */

public class CommonModelListRecyclerAdapter extends BaseQuickAdapter<CommonModel> {
    public CommonModelListRecyclerAdapter(List<CommonModel> data) {
        super(R.layout.item_common_model, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, CommonModel model) {
        holder.setText(R.id.name, StringUtils.replaceAllBlank(model.getName()));
        holder.setText(R.id.desc, StringUtils.trimNewLine(model.getDescription()));
    }
}
