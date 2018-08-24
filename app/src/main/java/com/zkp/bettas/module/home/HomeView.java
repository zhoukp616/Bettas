package com.zkp.bettas.module.home;

import com.zkp.bettas.module.home.bean.CateBean;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home
 * @time: 2018/8/15 11:03
 * @description:
 */
public interface HomeView {

    void showProgress();

    void hideProgress();

    void getCateListSuccess(CateBean cateBean);

    void getCateListError(int error);

}
