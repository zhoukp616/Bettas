package com.zkp.bettas.module.home;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zkp.bettas.R;
import com.zkp.bettas.base.BaseFragment;
import com.zkp.bettas.module.home.adapter.HomePagerAdapter;
import com.zkp.bettas.module.home.bean.CateBean;
import com.zkp.bettas.module.home.hotcate.HotCateFragment;
import com.zkp.bettas.module.home.other.OtherFragment;
import com.zkp.bettas.utils.ToastUtil;
import com.zkp.bettas.view.ProgressDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home
 * @time: 2018/8/15 11:38
 * @description:
 */
public class HomeFragment extends BaseFragment implements HomeView {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private HomePresenter presenter;
    private ProgressDialog dialog;

    private List<BaseFragment> fragments;
    private List<String> titles;

    @Override
    public View initView() {
        View rootView = View.inflate(context, R.layout.fragment_home, null);
        tabLayout = rootView.findViewById(R.id.tabLayout);
        viewPager = rootView.findViewById(R.id.viewPager);
        //关联viewpager
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }

    @Override
    public void initData() {
        super.initData();
        presenter = new HomePresenter();
        presenter.attachView(this);
        presenter.getCate();
    }

    @Override
    public void showProgress() {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.showMessage("加载中");
        }
        dialog.show();
    }

    @Override
    public void hideProgress() {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.showMessage("加载中");
        }
        dialog.dismiss();
    }

    @Override
    public void getCateListSuccess(CateBean cateBean) {
        fragments = new ArrayList<>();
        fragments.add(new HotCateFragment());
        titles = new ArrayList<>();
        titles.add("推荐");

        for (CateBean.DataBean dataBean : cateBean.getData()) {
            titles.add(dataBean.getTitle());
        }

        fragments.add(OtherFragment.newInstance(cateBean.getData().get(0).getIdentification(), 1));
        fragments.add(OtherFragment.newInstance(cateBean.getData().get(1).getIdentification(), 2));
        fragments.add(OtherFragment.newInstance(cateBean.getData().get(2).getIdentification(), 3));
        fragments.add(OtherFragment.newInstance(cateBean.getData().get(3).getIdentification(), 4));

        HomePagerAdapter pagerAdapter = new HomePagerAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void getCateListError(int error) {
        ToastUtil.showToast(context, "获取分类失败");
    }
}
