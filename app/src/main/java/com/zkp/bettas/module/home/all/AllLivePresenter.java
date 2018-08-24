package com.zkp.bettas.module.home.all;

import com.zkp.bettas.module.home.all.bean.ThreeCateBean;
import com.zkp.bettas.utils.RequestUtil;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.all
 * @time: 2018/8/24 11:07
 * @description:
 */
public class AllLivePresenter implements AllLiveBiz {

    private AllLiveView allLiveView;

    public void attachView(AllLiveView allLiveView) {
        if (this.allLiveView == null) {
            this.allLiveView = allLiveView;
        }
    }

    @Override
    public void getThreeCate(String tagId) {

        if (this.allLiveView != null) {
            allLiveView.showProgress();

            RequestUtil.request(RequestUtil.createApi(AllLiveApi.class).getThreeCate(tagId),
                    new RequestUtil.IResponseListener<ThreeCateBean>() {
                        @Override
                        public void onSuccess(ThreeCateBean data) {
                            allLiveView.getThreeCateSuccess(data);
                            allLiveView.hideProgress();
                        }

                        @Override
                        public void onFail() {
                            allLiveView.getThreeCateError(-1);
                            allLiveView.hideProgress();
                        }
                    });
        }

    }

    public void detachView() {
        if (this.allLiveView != null) {
            this.allLiveView = null;
        }
    }
}
