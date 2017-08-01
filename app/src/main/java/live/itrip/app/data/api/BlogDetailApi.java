package live.itrip.app.data.api;

import live.itrip.app.data.model.BlogDetailModel;
import rx.Observable;

/**
 * Created by Feng on 2017/7/24.
 */

public interface BlogDetailApi {
    Observable<BlogDetailModel> getBlogDetail(Long blogId);
}
