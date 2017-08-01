package live.itrip.app.data.net;

import javax.inject.Inject;

import live.itrip.app.data.api.BlogDetailApi;
import live.itrip.app.data.model.BlogDetailModel;
import live.itrip.app.data.net.response.BlogDetailResultResp;
import live.itrip.app.service.net.BlogDetailService;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Feng on 2017/7/24.
 */

public class BlogDetailDataSource implements BlogDetailApi {

    BlogDetailService mBlogDetailService;

    @Inject
    public BlogDetailDataSource(BlogDetailService service) {
        this.mBlogDetailService = service;
    }

    @Override
    public Observable<BlogDetailModel> getBlogDetail(Long blogId) {
        Observable<BlogDetailResultResp> blogDetail = mBlogDetailService.getBlogDetail(blogId);

        return blogDetail.map(new Func1<BlogDetailResultResp, BlogDetailModel>() {
            @Override
            public BlogDetailModel call(BlogDetailResultResp resp) {
                return resp.getBlogDetailModel();
            }
        });
    }
}
