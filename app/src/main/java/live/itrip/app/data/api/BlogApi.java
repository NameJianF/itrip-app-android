package live.itrip.app.data.api;

import live.itrip.app.data.model.BlogModel;
import rx.Observable;

/**
 * Created by Feng on 2017/7/24.
 */

public interface BlogApi {
    Observable<BlogModel> getBlogDetail(Long blogId);
}
