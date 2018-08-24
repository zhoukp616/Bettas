package com.zkp.bettas.module.home.other.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.other.adapter
 * @time: 2018/8/22 9:45
 * @description:
 */
public class ViewPagerAdapter extends PagerAdapter {

    private List<View> pageList;

    public ViewPagerAdapter(List<View> pageList) {
        this.pageList = pageList;
    }

    @Override
    public int getCount() {
        return pageList != null ? pageList.size() : 0;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(pageList.get(position));
        return pageList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(pageList.get(position));
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
