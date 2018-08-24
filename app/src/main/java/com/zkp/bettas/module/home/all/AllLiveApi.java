package com.zkp.bettas.module.home.all;

import com.zkp.bettas.module.home.all.bean.ThreeCateBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.all
 * @time: 2018/8/24 11:05
 * @description:
 */
public interface AllLiveApi {

    @GET("v1/getThreeCate?")
    Observable<ThreeCateBean> getThreeCate(
            @Query("tag_id") String tagId
    );

}
