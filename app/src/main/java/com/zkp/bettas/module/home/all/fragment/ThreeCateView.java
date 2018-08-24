package com.zkp.bettas.module.home.all.fragment;

import com.zkp.bettas.module.home.all.bean.AllBean;
import com.zkp.bettas.module.home.all.bean.CateTypeBean;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.all.fragment
 * @time: 2018/8/24 14:04
 * @description:
 */
public interface ThreeCateView {

    void showProgress();

    void hideProgress();

    void getCateTypeSuccess(CateTypeBean cateTypeBean);

    void getCateTypeError(int error);

    void getAllSuccess(AllBean allBean);

    void getAllError(int error);

}
