package com.zkp.bettas.module.common.phone;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.common.phone
 * @time: 2018/8/22 16:27
 * @description:
 */
public interface PhoneView {

    void showProgress();

    void hideProgress();

    void getVideoSuccess(String data);

    void getVideoError(int error);

}
