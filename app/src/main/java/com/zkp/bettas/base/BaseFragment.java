package com.zkp.bettas.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.base
 * @time: 2018/8/15 14:27
 * @description:
 */
public abstract class BaseFragment extends Fragment {

    public Activity context;

    /**
     * 当Fragment被创建的时候回调这个方法
     *
     * @param savedInstanceState bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    /**
     * 当视图被创建的时候回调
     *
     * @param inflater           inflater
     * @param container          container
     * @param savedInstanceState bundle
     * @return view
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 让孩子实现自己的视图，达到自己想要的效果
     *
     * @return view
     */
    public abstract View initView();

    /**
     * 当Activity被创建之后回调
     *
     * @param savedInstanceState bundle
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 如果子页面没有数据，联网请求数据，并绑定到initView初始化的视图上
     */
    public void initData() {

    }

}
