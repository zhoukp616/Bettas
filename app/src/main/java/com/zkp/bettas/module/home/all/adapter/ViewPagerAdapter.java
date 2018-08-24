package com.zkp.bettas.module.home.all.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zkp.bettas.module.home.all.bean.ThreeCateBean;
import com.zkp.bettas.module.home.all.fragment.ThreeCateFragment;

import java.util.List;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.all.adapter
 * @time: 2018/8/24 11:16
 * @description:
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<ThreeCateBean.DataBean> dataBeanList;
    private String[] titles;
    private String cateId;

    public ViewPagerAdapter(FragmentManager fm, List<ThreeCateBean.DataBean> dataBeanList,
                            String[] titles, String cateId) {
        super(fm);
        this.dataBeanList = dataBeanList;
        this.titles = titles;
        this.cateId = cateId;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ThreeCateFragment.newInstance(cateId, position);
        }
        return ThreeCateFragment.newInstance(dataBeanList.get(position - 1).getId(), position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
