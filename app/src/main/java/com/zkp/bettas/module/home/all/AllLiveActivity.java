package com.zkp.bettas.module.home.all;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.zkp.bettas.R;
import com.zkp.bettas.module.home.all.adapter.ViewPagerAdapter;
import com.zkp.bettas.module.home.all.bean.ThreeCateBean;
import com.zkp.bettas.utils.ToastUtil;
import com.zkp.bettas.view.ProgressDialog;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.all
 * @time: 2018/8/24 10:36
 * @description:
 */
public class AllLiveActivity extends AppCompatActivity implements AllLiveView {

    private ImageView ivBack, ivPopupLive;
    private TextView tvTitle;
    private RelativeLayout rlColumnBar, rlBar;
    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;

    private String tagId, cateId, title;
    private AllLivePresenter presenter;
    private ProgressDialog dialog;

    private String[] titles;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_live);

        initView();
    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        rlColumnBar = findViewById(R.id.rlColumnBar);
        rlBar = findViewById(R.id.rlBar);
        ivPopupLive = findViewById(R.id.ivPopupLive);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        initData();
    }

    private void initData() {
        tagId = getIntent().getExtras().getString("tagId");
        cateId = getIntent().getExtras().getString("cateId");
        title = getIntent().getExtras().getString("title");

        tvTitle.setText(title);

        presenter = new AllLivePresenter();
        presenter.attachView(this);
        presenter.getThreeCate(tagId);
    }

    @Override
    public void showProgress() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.showMessage("加载中...");
        }
        dialog.show();
    }

    @Override
    public void hideProgress() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.showMessage("加载中...");
        }
        dialog.dismiss();
    }

    @Override
    public void getThreeCateSuccess(ThreeCateBean threeCateBean) {
        titles = new String[threeCateBean.getData().size() + 1];
        titles[0] = "全部";
        for (int i = 0; i < threeCateBean.getData().size(); i++) {
            titles[i + 1] = threeCateBean.getData().get(i).getName();
        }
        if (titles.length <= 1) {
            rlColumnBar.setVisibility(View.GONE);
        }
        viewPager.setOffscreenPageLimit(titles.length);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), threeCateBean.getData(), titles, cateId);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager, titles);
    }

    @Override
    public void getThreeCateError(int error) {
        ToastUtil.showToast(this, "加载分类失败");
    }
}
