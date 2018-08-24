package com.zkp.bettas.module.common.pc;

import com.zkp.bettas.module.common.phone.bean.LiveBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.common.pc
 * @time: 2018/8/23 15:57
 * @description:
 */
public interface PcApi {

    @GET("html5/live?")
    Observable<LiveBean> getVideo(
            @Query("roomId") String roomId
    );

}
