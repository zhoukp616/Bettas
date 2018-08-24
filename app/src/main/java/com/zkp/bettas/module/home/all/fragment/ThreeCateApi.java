package com.zkp.bettas.module.home.all.fragment;

import com.zkp.bettas.module.home.all.bean.AllBean;
import com.zkp.bettas.module.home.all.bean.CateTypeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.all.fragment
 * @time: 2018/8/24 14:05
 * @description:
 */
public interface ThreeCateApi {

    @GET("v1/getThreeList?")
    Observable<CateTypeBean> getCateType(
            @Query("cate_id") String cateId,
            @Query("offset") int offset
    );

    @GET("v1/live/{id}?")
    Observable<AllBean> getAll(
            @Path("id") String id,
            @Query("offset") int offset
    );

}
