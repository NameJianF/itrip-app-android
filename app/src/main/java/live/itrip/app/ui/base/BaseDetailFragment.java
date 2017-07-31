package live.itrip.app.ui.base;


import live.itrip.app.presenter.interfaces.IDetailPresenter;

/**
 * Created by Feng on 2017/4/21.
 * <p>
 * 用于详情页
 */
public abstract class BaseDetailFragment extends BaseFragment {

    public abstract IDetailPresenter getDetailPresenter();
}
