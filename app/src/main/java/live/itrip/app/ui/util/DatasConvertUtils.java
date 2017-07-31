package live.itrip.app.ui.util;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

import live.itrip.app.config.Constants;
import live.itrip.app.data.model.ChildMultiItem;
import live.itrip.app.data.model.HomePageModel;

/**
 * Created by Feng on 2017/7/17.
 * <p>
 * 页面数据格式化
 */

public class DatasConvertUtils {

    /**
     * 1. banner
     * 2. nav  内置导航
     * 3. new plan  最新行程
     * 4. hot       热门行程
     * 5. list 猜你喜欢
     * 6. bolgs 热门博客
     *
     * @return
     */
    public static ArrayList<HomePageModel> converHomePageDatas() {
        // test data
//        ArrayList<HomePageModel> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            HomePageModel model = new HomePageModel();
//
//            if (i == 0) {
//                model.setItemType(HomePageModel.ITEM_BANNER);
//            } else if (i == 1) {
//                model.setItemType(HomePageModel.ITEM_NAV);
//            } else if (i == 2) {
//                model.setItemType(HomePageModel.ITEM_NEW_PLAN);
//            } else if (i == 3) {
//                model.setItemType(HomePageModel.ITEM_HOT);
//                ArrayList<ChildMultiItem> items = new ArrayList<>();
//                for (int j = 0; j < 4; j++) {
//                    ChildMultiItem bean = new ChildMultiItem();
//                    bean.setId(j + 1L);
//                    bean.setImageUrl(Constants.mBannerUrls[j]);
//                    bean.setTitle(Constants.mBannerNames[j]);
//                    bean.setItemType(ChildMultiItem.ITEM_PLAN);
//                    items.add(bean);
//                }
//                model.setItemList(items);
//            } else if (i == 4) {
//                model.setItemType(HomePageModel.ITEM_AD);
//            } else if (i == 5) {
//                model.setItemType(HomePageModel.ITEM_BLOG);
//                ArrayList<ChildMultiItem> items = new ArrayList<>();
//                for (int j = 0; j < 4; j++) {
//                    ChildMultiItem bean = new ChildMultiItem();
//                    bean.setId(j + 1L);
//                    bean.setImageUrl(Constants.mBannerUrls[j]);
//                    bean.setTitle(Constants.mBannerNames[j]);
//                    bean.setItemType(ChildMultiItem.ITEM_PLAN);
//                    items.add(bean);
//                }
//                model.setItemList(items);
//            } else if (i == 6) {
//                model.setItemType(HomePageModel.ITEM_AD);
//            } else {
//                model.setItemType(HomePageModel.ITEM_BLOG);
//                ArrayList<ChildMultiItem> items = new ArrayList<>();
//                for (int j = 0; j < 4; j++) {
//                    ChildMultiItem bean = new ChildMultiItem();
//                    bean.setId(j + 1L);
//                    bean.setImageUrl(Constants.mBannerUrls[j]);
//                    bean.setTitle(Constants.mBannerNames[j]);
//                    bean.setItemType(ChildMultiItem.ITEM_BOLG);
//                    items.add(bean);
//                }
//                model.setItemList(items);
//            }
//            if (i == 2) {
//                model.setImgUrl(Constants.mUrls[1]);
//                model.setTitle(Constants.mBannerNames[1]);
//                model.setContent(Constants.mSubTitles[1]);
//            } else {
//                model.setImgUrl(Constants.mUrls[i]);
//                model.setTitle(Constants.mBannerNames[i]);
//                model.setContent(Constants.mSubTitles[i]);
//            }
//            list.add(model);
//        }
//
//        System.err.println(JSON.toJSONString(list));
        return null;
    }
}
