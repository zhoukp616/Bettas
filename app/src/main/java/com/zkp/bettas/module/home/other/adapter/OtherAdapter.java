package com.zkp.bettas.module.home.other.adapter;

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

import com.zkp.bettas.R;
import com.zkp.bettas.module.common.pc.PcLiveActivity;
import com.zkp.bettas.module.common.phone.PhoneLiveActivity;
import com.zkp.bettas.module.home.other.bean.OtherBean;

import java.util.List;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.other.adapter
 * @time: 2018/8/17 15:53
 * @description:
 */
public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.ViewHolder> {

    private static final int TYPE_HEAD = 0xff01;
    private static final int TYPE_1 = 0xff02;

    private Context context;
    private List<OtherBean.DataBean> roomList;

    private View headView;

    private OtherRoomAdapter otherRoomAdapter;

    public OtherAdapter(Context context, List<OtherBean.DataBean> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    public void setHeadView(View headView) {
        this.headView = headView;
        notifyItemInserted(0);
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
                holder.ivIcon.setImageResource(R.drawable.icon_column);
                holder.tvName.setText(getItem(position).getTag_name());
                holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
                otherRoomAdapter = new OtherRoomAdapter(context, getItem(position).getRoom_list());
                holder.recyclerView.setAdapter(otherRoomAdapter);
                otherRoomAdapter.setOnItemClickListener(new OtherRoomAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, int p) {
                        if (getItem(position).getRoom_list().get(p).getCate_id() == 201) {
                            Intent intent = new Intent(context, PhoneLiveActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Room_id", String.valueOf(getItem(position).getRoom_list().get(p).getRoom_id()));
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        } else {
                            //电脑直播页面
                            Intent intent = new Intent(context, PcLiveActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Room_id", String.valueOf(getItem(position).getRoom_list().get(p).getRoom_id()));
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
        } else {
            return TYPE_1;
        }
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    private OtherBean.DataBean getItem(int position) {
        return roomList.get(position);
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
