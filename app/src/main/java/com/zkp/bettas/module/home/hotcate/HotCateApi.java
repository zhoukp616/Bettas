package com.zkp.bettas.module.home.hotcate;

import com.zkp.bettas.module.home.bean.BannerBean;
import com.zkp.bettas.module.home.bean.BigDataRoomBean;
import com.zkp.bettas.module.home.bean.CateTypeBean;
import com.zkp.bettas.module.home.bean.VerticalRoomBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.hotcate
 * @time: 2018/8/15 16:30
 * @description:
 */
public interface HotCateApi {

    @GET("v1/slide/6?version=2.421&client_sys=android")
    Observable<BannerBean> getBanner();

    @GET("v1/getHotCate?aid=android1&client_sys=android")
    Observable<CateTypeBean> getHotCate();

    //最热栏目
    @GET("v1/getbigDataRoom?aid=android1&client_sys=android")
    Observable<BigDataRoomBean> getBigDataRoom();

    //颜值栏目
    @GET("v1/getVerticalRoom?offset=0&limit=4&client_sys=android")
    Observable<VerticalRoomBean> getVerticalRoom();

}
