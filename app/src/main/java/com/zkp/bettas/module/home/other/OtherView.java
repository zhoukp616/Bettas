package com.zkp.bettas.module.home.other;

import com.zkp.bettas.module.home.other.bean.OtherBean;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.other
 * @time: 2018/8/17 15:03
 * @description:
 */
public interface OtherView {

    void showProgress();

    void hideProgress();

    void getHotRoomSuccess(OtherBean otherBean);

    void getHotRoomError(int error);

}
