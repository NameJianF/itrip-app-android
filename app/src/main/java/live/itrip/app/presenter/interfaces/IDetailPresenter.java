package live.itrip.app.presenter.interfaces;

/**
 * Created by Feng on 2017/7/28.
 */

public interface IDetailPresenter {

    void loadDetail(Long itemId);

    void favReverse();

    void addComment(Long id, int type, String commentText, int i, long mCommentId, long mCommentAuthorId);
}
