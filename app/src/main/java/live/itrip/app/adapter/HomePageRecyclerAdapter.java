package live.itrip.app.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import live.itrip.app.R;
import live.itrip.app.data.model.HomePageModel;

/**
 * Created by Feng on 2017/7/12.
 */

public class HomePageRecyclerAdapter extends BaseQuickAdapter<HomePageModel> {

    public HomePageRecyclerAdapter(List<HomePageModel> data) {
        super(R.layout.item_message, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomePageModel homePageModel) {

    }
}
