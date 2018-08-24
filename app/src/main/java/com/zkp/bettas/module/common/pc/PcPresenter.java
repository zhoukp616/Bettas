package com.zkp.bettas.module.common.pc;

import com.zkp.bettas.module.common.phone.bean.LiveBean;
import com.zkp.bettas.utils.LiveUtil;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.common.pc
 * @time: 2018/8/23 15:58
 * @description:
 */
public class PcPresenter implements PcBiz {

    private PcView pcView;

    public void attachView(PcView pcView) {
        if (this.pcView == null) {
            this.pcView = pcView;
        }
    }

    @Override
    public void getVideo(String roomId) {

        if (this.pcView != null) {
            pcView.showProgress();

            LiveUtil.request(LiveUtil.createApi(PcApi.class).getVideo(roomId),
                    new LiveUtil.IResponseListener<LiveBean>() {
                        @Override
                        public void onSuccess(LiveBean data) {
                            pcView.getVideoSuccess(data);
                            pcView.hideProgress();
                        }

                        @Override
                        public void onFail() {
                            pcView.getVideoError(-1);
                            pcView.hideProgress();
                        }
                    });
        }

    }

    public void detachView() {
        if (this.pcView != null) {
            this.pcView = null;
        }
    }
}
