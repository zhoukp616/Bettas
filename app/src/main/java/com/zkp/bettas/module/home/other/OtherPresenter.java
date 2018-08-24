package com.zkp.bettas.module.home.other;

import android.util.Log;

import com.zkp.bettas.module.home.other.bean.OtherBean;
import com.zkp.bettas.utils.RequestUtil;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.other
 * @time: 2018/8/17 15:04
 * @description:
 */
public class OtherPresenter implements OtherBiz {

    private OtherView otherView;

    public void attachView(OtherView otherView) {
        if (this.otherView == null) {
            this.otherView = otherView;
        }
    }

    @Override
    public void getHotRoom(String identification) {

        if (otherView != null) {
            otherView.showProgress();
            RequestUtil.request(RequestUtil.createApi(OtherApi.class).getHotRoom(identification),
                    new RequestUtil.IResponseListener<OtherBean>() {
                        @Override
                        public void onSuccess(OtherBean data) {
                            otherView.getHotRoomSuccess(data);
                            otherView.hideProgress();
                            Log.e("getHotRoom==", data.toString());
                        }

                        @Override
                        public void onFail() {
                            otherView.getHotRoomError(-1);
                            otherView.hideProgress();
                        }
                    });
        }

    }

    public void detachView() {
        if (this.otherView != null) {
            this.otherView = null;
        }
    }


}
