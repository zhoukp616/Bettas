package com.zkp.bettas.module.home;

import com.zkp.bettas.module.home.bean.CateBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home
 * @time: 2018/8/15 10:56
 * @description:
 */
public interface HomeApi {

    @GET("homeCate/getCateList?")
    Observable<CateBean> getCateList();

}
