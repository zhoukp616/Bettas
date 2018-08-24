package com.zkp.bettas.module.home.all.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zkp.bettas.R;
import com.zkp.bettas.base.BaseFragment;
import com.zkp.bettas.module.common.pc.PcLiveActivity;
import com.zkp.bettas.module.common.phone.PhoneLiveActivity;
import com.zkp.bettas.module.home.all.adapter.ThreeCateAdapter;
import com.zkp.bettas.module.home.all.adapter.ThreeCateAllAdapter;
import com.zkp.bettas.module.home.all.bean.AllBean;
import com.zkp.bettas.module.home.all.bean.CateTypeBean;
import com.zkp.bettas.utils.ToastUtil;
import com.zkp.bettas.view.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.all.fragment
 * @time: 2018/8/24 11:24
 * @description:
 */
public class ThreeCateFragment extends BaseFragment implements ThreeCateView, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeLayout;
    private RecyclerView recyclerView;

    private ThreeCatePresenter presenter;
    private ProgressDialog dialog;
    private ThreeCateAdapter adapter;
    private ThreeCateAllAdapter allAdapter;
    private int offsetAll = 0, offset = 0;

    private List<AllBean.DataBean> allBeanList;
    private List<CateTypeBean.DataBean> cateTypeBeanList;

    public static ThreeCateFragment newInstance(String cateId, int position) {
        Bundle args = new Bundle();
        args.putSerializable("cate_id", cateId);
        args.putSerializable("position", position);
        ThreeCateFragment fragment = new ThreeCateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView() {
        View rootView = View.inflate(context, R.layout.fragment_three_cate, null);
        swipeLayout = rootView.findViewById(R.id.swipeLayout);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        return rootView;
    }

    @Override
    public void initData() {
        super.initData();
        swipeLayout.setColorSchemeColors(context.getResources().getColor(R.color.colorPrimary));
        swipeLayout.setRefreshing(true);
        swipeLayout.setOnRefreshListener(this);

        presenter = new ThreeCatePresenter();
        presenter.attachView(this);

        if ((getArguments() != null ? getArguments().getInt("position") : 0) == 0) {
            presenter.getAll(getArguments() != null ? getArguments().getString("cate_id") : null, offsetAll++);
        } else {
            presenter.getCateType(getArguments() != null ? getArguments().getString("cate_id") : null, offset++);
        }
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
    public void getCateTypeSuccess(CateTypeBean cateTypeBean) {
        swipeLayout.setRefreshing(false);

        if (cateTypeBeanList == null) {
            cateTypeBeanList = new ArrayList<>();
        }

        cateTypeBeanList.addAll(cateTypeBean.getData());

        if (adapter == null) {
            adapter = new ThreeCateAdapter(context, cateTypeBeanList);
            recyclerView.setAdapter(adapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    //设置什么布局管理器,就获取什么的布局管理器
                    GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
                    // 当停止滑动时
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //获取最后一个完全显示的ItemPosition ,角标值
                        int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                        //所有条目,数量值
                        int totalItemCount = manager.getItemCount();
                        // 判断是否滚动到底部
                        if (lastVisibleItem == (totalItemCount - 1)) {
                            //加载更多功能的代码
                            presenter.getCateType(getArguments() != null ? getArguments().getString("cate_id") : null, offset++);
                        }
                    }
                }
            });

            adapter.setOnItemClickListener(new ThreeCateAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, int position) {
                    //电脑直播页面
                    if (adapter.getItem(position).getCate_id() == 201) {
                        Intent intent = new Intent(context, PhoneLiveActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Room_id", adapter.getItem(position).getRoom_id());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, PcLiveActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Room_id", adapter.getItem(position).getRoom_id());
                        bundle.putString("roomName", adapter.getItem(position).getRoom_name());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getCateTypeError(int error) {
        swipeLayout.setRefreshing(false);
        ToastUtil.showToast(context, "获取二级分类失败");
    }

    @Override
    public void getAllSuccess(AllBean allBean) {
        swipeLayout.setRefreshing(false);

        if (allBeanList == null) {
            allBeanList = new ArrayList<>();
        }
        allBeanList.addAll(allBean.getData());

        if (allAdapter == null) {
            allAdapter = new ThreeCateAllAdapter(context, allBeanList);
            recyclerView.setAdapter(allAdapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    //设置什么布局管理器,就获取什么的布局管理器
                    GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
                    // 当停止滑动时
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //获取最后一个完全显示的ItemPosition ,角标值
                        int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                        //所有条目,数量值
                        int totalItemCount = manager.getItemCount();
                        // 判断是否滚动到底部
                        if (lastVisibleItem == (totalItemCount - 1)) {
                            //加载更多功能的代码
                            presenter.getAll(getArguments() != null ? getArguments().getString("cate_id") : null, offsetAll++);
                        }
                    }
                }
            });

            allAdapter.setOnItemClickListener(new ThreeCateAllAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, int position) {
                    if (allAdapter.getItem(position).getCate_id() == 201) {
                        Intent intent = new Intent(context, PhoneLiveActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Room_id", allAdapter.getItem(position).getRoom_id());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, PcLiveActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Room_id", allAdapter.getItem(position).getRoom_id());
                        bundle.putString("roomName", allAdapter.getItem(position).getRoom_name());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });
        } else {
            allAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getAllError(int error) {
        swipeLayout.setRefreshing(false);
        ToastUtil.showToast(context, "获取所有视频失败");
    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(true);
        if ((getArguments() != null ? getArguments().getInt("position") : 0) == 0) {
            offsetAll = 0;
            presenter.getAll(getArguments() != null ? getArguments().getString("cate_id") : null, offsetAll++);
        } else {
            offset = 0;
            presenter.getCateType(getArguments() != null ? getArguments().getString("cate_id") : null, offset++);
        }
    }
}
