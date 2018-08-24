package com.zkp.bettas.module.common.phone;

import com.zkp.bettas.module.common.phone.bean.LiveBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.common.phone
 * @time: 2018/8/22 16:28
 * @description:
 */
public interface PhoneApi {

    @GET("html5/live?")
    Observable<LiveBean> getVideo(
            @Query("roomId") String roomId
    );


}
