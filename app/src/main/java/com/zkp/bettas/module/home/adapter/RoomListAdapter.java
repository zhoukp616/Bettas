package com.zkp.bettas.module.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zkp.bettas.R;
import com.zkp.bettas.module.common.pc.PcLiveActivity;
import com.zkp.bettas.module.common.phone.PhoneLiveActivity;
import com.zkp.bettas.module.home.bean.BigDataRoomBean;
import com.zkp.bettas.module.home.bean.CateTypeBean;
import com.zkp.bettas.module.home.bean.VerticalRoomBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.adapter
 * @time: 2018/8/17 9:03
 * @description:
 */
public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ViewHolder> {

    private static final int TYPE_HEAD = 0xff01;
    private static final int TYPE_1 = 0xff02;
    private static final int TYPE_2 = 0xff03;
    private static final int TYPE_3 = 0xff04;

    private Context context;

    private View headView;

    /**
     * 最热栏目
     */
    private List<BigDataRoomBean.DataBean> bigDataRoomList;
    /**
     * 颜值栏目
     */
    private List<VerticalRoomBean.DataBean> verticalRoomList;
    /**
     * 其他分类
     */
    private List<CateTypeBean.DataBean> cateTypeBeanList;

    private BigDataRoomAdapter bigDataRoomAdapter;
    private VerticalRoomAdapter verticalRoomAdapter;
    private CateRoomAdapter cateRoomAdapter;

    public RoomListAdapter(Context context) {
        this.context = context;
        this.bigDataRoomList = new ArrayList<>();
        this.verticalRoomList = new ArrayList<>();
        this.cateTypeBeanList = new ArrayList<>();
    }

    public void setHeadView(View headView) {
        this.headView = headView;
        notifyItemInserted(0);
    }

    public void setBigDataRoomList(List<BigDataRoomBean.DataBean> bigDataRoomList) {
        this.bigDataRoomList.clear();
        this.bigDataRoomList.addAll(bigDataRoomList);
        notifyItemInserted(1);
    }

    public void setVerticalRoomList(List<VerticalRoomBean.DataBean> verticalRoomList) {
        this.verticalRoomList.clear();
        this.verticalRoomList.addAll(verticalRoomList);
        notifyItemInserted(2);
    }

    public void setCateTypeBeanList(List<CateTypeBean.DataBean> cateTypeBeanList) {
        this.cateTypeBeanList.clear();
        this.cateTypeBeanList.addAll(cateTypeBeanList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            return new ViewHolder(headView);
        } else {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_room_list, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        switch (getItemViewType(position)) {
            case TYPE_1:
                //最热栏目
                holder.ivIcon.setImageResource(R.drawable.icon_hot);
                holder.tvName.setText("最热");
                bigDataRoomAdapter = new BigDataRoomAdapter(context, bigDataRoomList);
                holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                holder.recyclerView.setAdapter(bigDataRoomAdapter);
                bigDataRoomAdapter.setOnItemClickListener(new BigDataRoomAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int p) {
                        if (bigDataRoomList.get(p).getCate_id() == 201) {
                            Intent intent = new Intent(context, PhoneLiveActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Room_id", String.valueOf(bigDataRoomList.get(p).getRoom_id()));
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        } else {
                            //电脑直播页面
                            Intent intent = new Intent(context, PcLiveActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Room_id", String.valueOf(bigDataRoomList.get(p).getRoom_id()));
                            bundle.putString("roomName", bigDataRoomList.get(p).getRoom_name());
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    }
                });
                break;
            case TYPE_2:
                //颜值栏目
                holder.ivIcon.setImageResource(R.drawable.icon_reco_mobile);
                holder.tvName.setText("颜值");
                verticalRoomAdapter = new VerticalRoomAdapter(context, verticalRoomList);
                holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                holder.recyclerView.setAdapter(verticalRoomAdapter);
                //颜值直播房间详情页
                verticalRoomAdapter.setOnItemClickListener(new VerticalRoomAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int p) {
                        //手机直播页面
                        Intent intent = new Intent(context, PhoneLiveActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("Room_id", verticalRoomList.get(p).getRoom_id());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
                break;
            case TYPE_3:
                //其他栏目
                Glide.with(context).load(getItem(position).getSmall_icon_url()).into(holder.ivIcon);
                holder.tvName.setText(getItem(position).getTag_name());
                cateRoomAdapter = new CateRoomAdapter(context, getItem(position).getRoom_list());
                holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                holder.recyclerView.setAdapter(cateRoomAdapter);
                cateRoomAdapter.setOnItemClickListener(new CateRoomAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int p) {
                        //电脑直播页面
                        if (getItem(position).getRoom_list().get(p).getCate_id() == 201) {
                            Intent intent = new Intent(context, PhoneLiveActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Room_id", getItem(position).getRoom_list().get(p).getRoom_id());
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, PcLiveActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Room_id", getItem(position).getRoom_list().get(p).getRoom_id());
                            bundle.putString("roomName", getItem(position).getRoom_list().get(p).getRoom_name());
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else if (position == 1) {
            return TYPE_1;
        } else if (position == 2) {
            return TYPE_2;
        } else {
            return TYPE_3;
        }
    }

    @Override
    public int getItemCount() {
        return cateTypeBeanList.size() + 2;
    }

    private CateTypeBean.DataBean getItem(int position) {
        return cateTypeBeanList.get(position - 2);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon, ivMore;
        TextView tvName, tvMore;
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);

            if (itemView == headView) {
                return;
            }

            ivIcon = itemView.findViewById(R.id.ivIcon);
            ivMore = itemView.findViewById(R.id.ivMore);
            tvName = itemView.findViewById(R.id.tvName);
            tvMore = itemView.findViewById(R.id.tvMore);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }
}
