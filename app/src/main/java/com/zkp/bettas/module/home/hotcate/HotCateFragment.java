package com.zkp.bettas.module.home.hotcate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zkp.bettas.R;
import com.zkp.bettas.base.BaseFragment;
import com.zkp.bettas.module.home.adapter.HotCateTypeAdapter;
import com.zkp.bettas.module.home.adapter.RoomListAdapter;
import com.zkp.bettas.module.home.all.AllLiveActivity;
import com.zkp.bettas.module.home.bean.BannerBean;
import com.zkp.bettas.module.home.bean.BigDataRoomBean;
import com.zkp.bettas.module.home.bean.CateTypeBean;
import com.zkp.bettas.module.home.bean.VerticalRoomBean;
import com.zkp.bettas.utils.DensityUtil;
import com.zkp.bettas.utils.GlideImageLoader;
import com.zkp.bettas.utils.SpacesItemDecoration;
import com.zkp.bettas.utils.ToastUtil;
import com.zkp.bettas.view.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.hotcate
 * @time: 2018/8/15 14:49
 * @description:
 */
public class HotCateFragment extends BaseFragment implements HotCateView, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeLayout;
    private View headView;
    private Banner banner;
    private RecyclerView recyclerView, recyclerViewVideo;

    private HotCatePresenter presenter;
    private ProgressDialog dialog;

    private RoomListAdapter roomListAdapter;

    @Override
    public View initView() {
        View rootView = View.inflate(context, R.layout.fragment_hotcate, null);
        swipeLayout = rootView.findViewById(R.id.swipeLayout);

        recyclerViewVideo = rootView.findViewById(R.id.recyclerViewVideo);
        recyclerViewVideo.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerViewVideo.addItemDecoration(new SpacesItemDecoration(DensityUtil.dip2px(context, 10)));

        headView = View.inflate(context, R.layout.item_cate_head, null);
        banner = headView.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setDelayTime(2000);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        recyclerView = headView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        return rootView;
    }

    @Override
    public void initData() {
        super.initData();
        swipeLayout.setColorSchemeColors(context.getResources().getColor(R.color.colorPrimary));
        swipeLayout.setRefreshing(true);
        swipeLayout.setOnRefreshListener(this);
        presenter = new HotCatePresenter();
        presenter.attachView(this);
        presenter.getBanner();
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
    public void getBannerSuccess(BannerBean bannerBean) {
        List<String> titles = new ArrayList<>();
        List<String> imageUrls = new ArrayList<>();

        for (BannerBean.DataBean dataBean : bannerBean.getData()) {
            titles.add(dataBean.getTitle());
            imageUrls.add(dataBean.getPic_url());
        }

        banner.setImages(imageUrls);
        banner.setBannerTitles(titles);
        banner.start();

        presenter.getHotCate();
    }

    @Override
    public void getBannerError(int error) {
        ToastUtil.showToast(context, "加载轮播图失败");
    }

    @Override
    public void getHotCateSuccess(final CateTypeBean cateTypeBean) {
        presenter.getBigDataRoom();
        HotCateTypeAdapter adapter = new HotCateTypeAdapter(context, cateTypeBean);
        adapter.setOnItemClickListener(new HotCateTypeAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Intent intent = new Intent(context, AllLiveActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tagId", cateTypeBean.getData().get(position).getTag_id());
                bundle.putString("cateId", cateTypeBean.getData().get(position).getTag_id());
                bundle.putString("title", cateTypeBean.getData().get(position).getTag_name());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        roomListAdapter = new RoomListAdapter(context);
        roomListAdapter.setHeadView(headView);
        roomListAdapter.setCateTypeBeanList(cateTypeBean.getData());
    }

    @Override
    public void getHotCateError(int error) {
        ToastUtil.showToast(context, "加载分类失败");
    }

    @Override
    public void getBigDataRoomSuccess(BigDataRoomBean bigDataRoomBean) {
        presenter.getVerticalRoom();
        recyclerViewVideo.setAdapter(roomListAdapter);
        roomListAdapter.setBigDataRoomList(bigDataRoomBean.getData());
    }

    @Override
    public void getBigDataRoomError(int error) {
        ToastUtil.showToast(context, "加载最热房间信息失败");
    }

    @Override
    public void getVerticalRoomSuccess(VerticalRoomBean verticalRoomBean) {
        swipeLayout.setRefreshing(false);
        roomListAdapter.setVerticalRoomList(verticalRoomBean.getData());
    }

    @Override
    public void getVerticalRoomError(int error) {
        swipeLayout.setRefreshing(false);
        ToastUtil.showToast(context, "加载颜值房间信息失败");
    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(true);
        presenter.getBanner();
    }
}
