package com.zkp.bettas.module.common.pc;

import com.zkp.bettas.module.common.phone.bean.LiveBean;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.common.pc
 * @time: 2018/8/23 15:56
 * @description:
 */
public interface PcView {

    void showProgress();

    void hideProgress();

    void getVideoSuccess(LiveBean liveBean);

    void getVideoError(int error);

}
