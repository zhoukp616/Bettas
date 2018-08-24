package com.zkp.bettas.module.home.all.fragment;

import com.zkp.bettas.module.home.all.bean.AllBean;
import com.zkp.bettas.module.home.all.bean.CateTypeBean;
import com.zkp.bettas.module.home.all.bean.ThreeCateBean;
import com.zkp.bettas.utils.RequestUtil;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.all.fragment
 * @time: 2018/8/24 14:07
 * @description:
 */
public class ThreeCatePresenter implements ThreeCateBiz {

    private ThreeCateView threeCateView;

    public void attachView(ThreeCateView threeCateView) {
        if (this.threeCateView == null) {
            this.threeCateView = threeCateView;
        }
    }

    @Override
    public void getCateType(String cateId, int offset) {

        if (this.threeCateView != null) {

            threeCateView.showProgress();

            RequestUtil.request(RequestUtil.createApi(ThreeCateApi.class).getCateType(cateId, offset),
                    new RequestUtil.IResponseListener<CateTypeBean>() {
                        @Override
                        public void onSuccess(CateTypeBean data) {
                            threeCateView.getCateTypeSuccess(data);
                            threeCateView.hideProgress();
                        }

                        @Override
                        public void onFail() {
                            threeCateView.getCateTypeError(-1);
                            threeCateView.hideProgress();
                        }
                    });
        }
    }

    @Override
    public void getAll(String id, int offset) {

        if (this.threeCateView != null) {

            threeCateView.showProgress();

            RequestUtil.request(RequestUtil.createApi(ThreeCateApi.class).getAll(id, offset),
                    new RequestUtil.IResponseListener<AllBean>() {
                        @Override
                        public void onSuccess(AllBean data) {
                            threeCateView.getAllSuccess(data);
                            threeCateView.hideProgress();
                        }

                        @Override
                        public void onFail() {
                            threeCateView.getAllError(-1);
                            threeCateView.hideProgress();
                        }
                    });

        }

    }

    public void detachView() {
        if (this.threeCateView != null) {
            this.threeCateView = null;
        }
    }
}
