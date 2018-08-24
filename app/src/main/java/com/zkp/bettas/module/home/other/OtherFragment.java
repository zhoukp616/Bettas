package com.zkp.bettas.module.home.other;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zkp.bettas.R;
import com.zkp.bettas.base.BaseFragment;
import com.zkp.bettas.module.home.other.adapter.OtherAdapter;
import com.zkp.bettas.module.home.other.adapter.GridAdapter;
import com.zkp.bettas.module.home.other.adapter.ViewPagerAdapter;
import com.zkp.bettas.module.home.other.bean.OtherBean;
import com.zkp.bettas.utils.DensityUtil;
import com.zkp.bettas.utils.SpacesItemDecoration;
import com.zkp.bettas.utils.ToastUtil;
import com.zkp.bettas.view.ProgressDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.other
 * @time: 2018/8/17 15:00
 * @description:
 */
public class OtherFragment extends BaseFragment implements OtherView, ViewPager.OnPageChangeListener {

    private static List<OtherFragment> mOtherFragments = new ArrayList<>();

    private String identification;
    private SwipeRefreshLayout swipeLayout;
    private View headView;
    private ViewPager viewPager;
    private LinearLayout points;

    private RecyclerView recyclerViewVideo;

    private OtherPresenter presenter;
    private ProgressDialog dialog;

    private OtherAdapter adapter;
    private GridAdapter gridAdapter;
    private ViewPagerAdapter pagerAdapter;

    //小圆点图片集合
    private ImageView[] mIvPoints;

    public static OtherFragment newInstance(String identification, int position) {
        OtherFragment fragment = new OtherFragment();
        Bundle args = new Bundle();
        args.putString("identification", identification);
        args.putInt("position", position - 1);
        fragment.setArguments(args);
        mOtherFragments.add(position - 1, fragment);
        return fragment;
    }

    @Override
    public View initView() {
        View rootView = View.inflate(context, R.layout.fragment_other, null);
        swipeLayout = rootView.findViewById(R.id.swipeLayout);
        recyclerViewVideo = rootView.findViewById(R.id.recyclerViewVideo);
        recyclerViewVideo.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        recyclerViewVideo.addItemDecoration(new SpacesItemDecoration(DensityUtil.dip2px(context, 10)));

        headView = View.inflate(context, R.layout.item_other_head, null);
        viewPager = headView.findViewById(R.id.viewPager);
        points = headView.findViewById(R.id.points);

        return rootView;
    }

    @Override
    public void initData() {
        super.initData();
        swipeLayout.setColorSchemeColors(context.getResources().getColor(R.color.colorPrimary));
        presenter = new OtherPresenter();
        presenter.attachView(this);
        identification = getArguments() != null ? getArguments().getString("identification") : null;
        presenter.getHotRoom(identification);
    }

    @Override
    public void showProgress() {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.showMessage("加载中...");
        }
        dialog.show();
    }

    @Override
    public void hideProgress() {
        if (dialog == null) {
            dialog = new ProgressDialog(context);
            dialog.showMessage("加载中...");
        }
        dialog.dismiss();
    }

    @Override
    public void getHotRoomSuccess(OtherBean otherBean) {
        List<OtherBean.DataBean> roomList = new ArrayList<>(otherBean.getData());
        ListIterator<OtherBean.DataBean> listIterator = roomList.listIterator();

        while (listIterator.hasNext()) {
            if (listIterator.next().getRoom_list().size() < 4) {
                listIterator.remove();
            }
        }

        adapter = new OtherAdapter(context, roomList);
        recyclerViewVideo.setAdapter(adapter);
        getHeadView(roomList);
        adapter.setHeadView(headView);
    }

    private void getHeadView(List<OtherBean.DataBean> data) {
        //总共多少页
        int mTotalPage;
        //每页显示的最大数量  默认为8
        int mPageSize = 8;
        //GridView 作为一个View对象添加到ViewPager集合中
        List<View> mViewPageList;
        Bundle arguments = getArguments();
        viewPager.removeOnPageChangeListener(mOtherFragments.get(arguments != null ? arguments.getInt("position") : 0));
        viewPager.addOnPageChangeListener(mOtherFragments.get(arguments != null ? arguments.getInt("position") : 0));
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        //显示总的页数  Math.ceil 先上取整
        mTotalPage = (int) Math.ceil(data.size() * 1.0 / mPageSize);
        mViewPageList = new ArrayList<>();
        data.remove(0);
        for (int i = 0; i < mTotalPage; i++) {
            GridView gridView = (GridView) inflater.inflate(R.layout.view_layout_gridview, null);
            gridAdapter = new GridAdapter(context, data, i, mPageSize);
            gridView.setAdapter(gridAdapter);
            //每一个GridView 作为一个View 对象添加到ViewPage集合中
            mViewPageList.add(gridView);
        }
        pagerAdapter = new ViewPagerAdapter(mViewPageList);
        viewPager.setAdapter(pagerAdapter);
        //创建小圆点
        mIvPoints = null;
        mIvPoints = new ImageView[2];
        for (int i = 0; i < mIvPoints.length; i++) {
            ImageView imgView = new ImageView(getActivity());
            //设置小圆点宽和高
            imgView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            //默认设置
            if (i == 0) {
                imgView.setBackgroundResource(R.drawable.page__selected_indicator);
            } else {
                imgView.setBackgroundResource(R.drawable.page__normal_indicator);
            }
            mIvPoints[i] = imgView;
            //设置边距
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            points.addView(imgView, layoutParams);
        }
        if (mTotalPage == 1) {
            points.setVisibility(View.GONE);
        }
    }

    @Override
    public void getHotRoomError(int error) {
        ToastUtil.showToast(context, "获取列表数据失败");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < mIvPoints.length; i++) {
            if (i == position) {
                mIvPoints[i].setBackgroundResource(R.drawable.page__selected_indicator);
            } else {
                mIvPoints[i].setBackgroundResource(R.drawable.page__normal_indicator);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
