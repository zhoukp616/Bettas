package com.zkp.bettas.module.home.all.fragment;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.all.fragment
 * @time: 2018/8/24 14:06
 * @description:
 */
public interface ThreeCateBiz {

    void getCateType(String cateId, int offset);

    void getAll(String id, int offset);

}
