package com.zkp.bettas.module.home;

import com.zkp.bettas.module.home.bean.CateBean;
import com.zkp.bettas.utils.RequestUtil;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home
 * @time: 2018/8/15 11:22
 * @description:
 */
public class HomePresenter implements HomeBiz {

    private HomeView homeView;

    public void attachView(HomeView homeView) {
        if (this.homeView == null) {
            this.homeView = homeView;
        }
    }

    @Override
    public void getCate() {
        RequestUtil.request(RequestUtil.createApi(HomeApi.class).getCateList(),
                new RequestUtil.IResponseListener<CateBean>() {
                    @Override
                    public void onSuccess(CateBean data) {
                        homeView.getCateListSuccess(data);
                        homeView.hideProgress();
                    }

                    @Override
                    public void onFail() {
                        homeView.getCateListError(-1);
                        homeView.hideProgress();
                    }
                });
    }

    public void detachView() {
        if (this.homeView != null) {
            this.homeView = null;
        }
    }
}
