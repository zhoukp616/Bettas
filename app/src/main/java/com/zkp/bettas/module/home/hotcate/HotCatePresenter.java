package com.zkp.bettas.module.home.hotcate;

import com.zkp.bettas.module.home.bean.BannerBean;
import com.zkp.bettas.module.home.bean.BigDataRoomBean;
import com.zkp.bettas.module.home.bean.CateTypeBean;
import com.zkp.bettas.module.home.bean.VerticalRoomBean;
import com.zkp.bettas.utils.RequestUtil;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.hotcate
 * @time: 2018/8/15 16:40
 * @description:
 */
public class HotCatePresenter implements HotCateBiz {

    private HotCateView hotCateView;

    public void attachView(HotCateView hotCateView) {
        if (this.hotCateView == null) {
            this.hotCateView = hotCateView;
        }
    }

    @Override
    public void getBanner() {
        if (hotCateView != null) {
            hotCateView.showProgress();

            RequestUtil.request(RequestUtil.createApi(HotCateApi.class).getBanner(),
                    new RequestUtil.IResponseListener<BannerBean>() {
                        @Override
                        public void onSuccess(BannerBean data) {
                            hotCateView.getBannerSuccess(data);
                            hotCateView.hideProgress();
                        }

                        @Override
                        public void onFail() {
                            hotCateView.getBannerError(-1);
                            hotCateView.hideProgress();
                        }
                    });
        }
    }

    @Override
    public void getHotCate() {
        if (hotCateView != null) {

            hotCateView.showProgress();

            RequestUtil.request(RequestUtil.createApi(HotCateApi.class).getHotCate(),
                    new RequestUtil.IResponseListener<CateTypeBean>() {
                        @Override
                        public void onSuccess(CateTypeBean data) {
                            hotCateView.getHotCateSuccess(data);
                            hotCateView.hideProgress();
                        }

                        @Override
                        public void onFail() {
                            hotCateView.getHotCateError(-1);
                            hotCateView.hideProgress();
                        }
                    });

        }
    }

    @Override
    public void getBigDataRoom() {
        if (hotCateView != null) {
            hotCateView.showProgress();

            RequestUtil.request(RequestUtil.createApi(HotCateApi.class).getBigDataRoom(),
                    new RequestUtil.IResponseListener<BigDataRoomBean>() {
                        @Override
                        public void onSuccess(BigDataRoomBean data) {
                            hotCateView.getBigDataRoomSuccess(data);
                            hotCateView.hideProgress();
                        }

                        @Override
                        public void onFail() {
                            hotCateView.getBigDataRoomError(-1);
                            hotCateView.hideProgress();
                        }
                    });
        }
    }

    @Override
    public void getVerticalRoom() {
        if (hotCateView != null) {
            hotCateView.showProgress();

            RequestUtil.request(RequestUtil.createApi(HotCateApi.class).getVerticalRoom(),
                    new RequestUtil.IResponseListener<VerticalRoomBean>() {
                        @Override
                        public void onSuccess(VerticalRoomBean data) {
                            hotCateView.getVerticalRoomSuccess(data);
                            hotCateView.hideProgress();
                        }

                        @Override
                        public void onFail() {
                            hotCateView.getVerticalRoomError(-1);
                            hotCateView.hideProgress();
                        }
                    });
        }
    }

    public void detachView() {
        if (this.hotCateView != null) {
            this.hotCateView = null;
        }
    }
}
