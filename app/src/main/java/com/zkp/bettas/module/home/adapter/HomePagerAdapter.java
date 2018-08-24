package com.zkp.bettas.module.home.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zkp.bettas.base.BaseFragment;

import java.util.List;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.adapter
 * @time: 2018/8/15 14:18
 * @description:
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> baseFragments;
    private List<String> titles;

    public HomePagerAdapter(FragmentManager fm, List<BaseFragment> baseFragments, List<String> titles) {
        super(fm);
        this.baseFragments = baseFragments;
        this.titles = titles;
    }

    public void setBaseFragments(List<BaseFragment> baseFragments) {
        this.baseFragments = baseFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return baseFragments.get(position);
    }

    @Override
    public int getCount() {
        return baseFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
