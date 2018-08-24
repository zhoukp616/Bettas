package com.zkp.bettas.module.home.hotcate;

import com.zkp.bettas.module.home.bean.BannerBean;
import com.zkp.bettas.module.home.bean.BigDataRoomBean;
import com.zkp.bettas.module.home.bean.CateTypeBean;
import com.zkp.bettas.module.home.bean.VerticalRoomBean;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.hotcate
 * @time: 2018/8/15 16:38
 * @description:
 */
public interface HotCateView {

    void showProgress();

    void hideProgress();

    void getBannerSuccess(BannerBean bannerBean);

    void getBannerError(int error);

    void getHotCateSuccess(CateTypeBean cateTypeBean);

    void getHotCateError(int error);

    void getBigDataRoomSuccess(BigDataRoomBean bigDataRoomBean);

    void getBigDataRoomError(int error);

    void getVerticalRoomSuccess(VerticalRoomBean verticalRoomBean);

    void getVerticalRoomError(int error);
}
