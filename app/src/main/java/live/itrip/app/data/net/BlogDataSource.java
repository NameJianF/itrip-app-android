package live.itrip.app.data.net;

import javax.inject.Inject;

import live.itrip.app.data.api.BlogApi;
import live.itrip.app.data.model.BlogModel;
import live.itrip.app.data.net.response.BlogResultResp;
import live.itrip.app.service.net.BlogService;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Feng on 2017/7/24.
 */

public class BlogDataSource implements BlogApi {

    BlogService mBlogService;

    @Inject
    public BlogDataSource(BlogService service) {
        this.mBlogService = service;
    }

    @Override
    public Observable<BlogModel> getBlogDetail(Long blogId) {
        Observable<BlogResultResp> blogDetail = mBlogService.getBlogDetail(blogId);

        return blogDetail.map(new Func1<BlogResultResp, BlogModel>() {
            @Override
            public BlogModel call(BlogResultResp resp) {
                return resp.getBlogModel();
            }
        });
    }
}
