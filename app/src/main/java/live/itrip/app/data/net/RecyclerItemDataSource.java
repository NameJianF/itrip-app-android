package live.itrip.app.data.net;

import java.util.ArrayList;

import javax.inject.Inject;

import live.itrip.app.config.Constants;
import live.itrip.app.data.api.RecyclerItemListApi;
import live.itrip.app.data.model.RecyclerViewItem;
import live.itrip.app.data.net.client.RecyclerItemRetrofit;
import live.itrip.app.data.net.response.RecyclerItemResultResp;
import live.itrip.app.service.net.RecyclerItemDataService;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Feng on 2017/6/26.
 */

public class RecyclerItemDataSource implements RecyclerItemListApi {

    private static final String SORT_BY_CREATETIME = "createTime";
    private static final String ORDER_BY_DESC = "desc";

    private RecyclerItemDataService mRecyclerItemDataService;

    @Inject
    public RecyclerItemDataSource(RecyclerItemDataService service) {
        this.mRecyclerItemDataService = service;
    }


    @Override
    public Observable<ArrayList<RecyclerViewItem>> getItemList(@RecyclerItemListApi.RecyclerItemType int type, Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        return this.mRecyclerItemDataService.getItemList(type, SORT_BY_CREATETIME, ORDER_BY_DESC, page, Constants.PAGE_SIZE)
                .map(new Func1<RecyclerItemResultResp, ArrayList<RecyclerViewItem>>() {
                    @Override
                    public ArrayList<RecyclerViewItem> call(RecyclerItemResultResp searchResultResp) {
                        return searchResultResp.getItems();
                    }
                });
    }

}
