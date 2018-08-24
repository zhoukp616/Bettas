package com.zkp.bettas.module.home.all;

import com.zkp.bettas.module.home.all.bean.ThreeCateBean;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.all
 * @time: 2018/8/24 11:02
 * @description:
 */
public interface AllLiveView {

    void showProgress();

    void hideProgress();

    void getThreeCateSuccess(ThreeCateBean threeCateBean);

    void getThreeCateError(int error);

}
