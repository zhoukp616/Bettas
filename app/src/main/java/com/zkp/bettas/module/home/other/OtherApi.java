package com.zkp.bettas.module.home.other;

import com.zkp.bettas.module.home.other.bean.OtherBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.other
 * @time: 2018/8/17 15:03
 * @description:
 */
public interface OtherApi {

    @POST("homeCate/getHotRoom?")
    Observable<OtherBean> getHotRoom(
            @Query("identification") String identification);
}
