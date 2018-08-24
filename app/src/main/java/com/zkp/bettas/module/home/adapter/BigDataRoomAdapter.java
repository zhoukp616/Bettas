package com.zkp.bettas.module.home.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zkp.bettas.R;
import com.zkp.bettas.module.home.bean.BigDataRoomBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.adapter
 * @time: 2018/8/17 9:57
 * @description:
 */
public class BigDataRoomAdapter extends RecyclerView.Adapter<BigDataRoomAdapter.ViewHolder> {

    private Context context;
    private List<BigDataRoomBean.DataBean> bigDataRoomList;

    private OnItemClickListener onItemClickListener;

    public BigDataRoomAdapter(Context context, List<BigDataRoomBean.DataBean> bigDataRoomList) {
        this.context = context;
        this.bigDataRoomList = bigDataRoomList;
    }

    /**
     * 设置item的点击事件
     *
     * @param onItemClickListener onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_big_data_room, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.simpleDraweeView.setImageURI(Uri.parse(getItem(position).getVertical_src()));
        holder.tvName.setText(getItem(position).getRoom_name());
        holder.tvNickname.setText(getItem(position).getNickname());
        holder.tvOnlineNum.setText(getOnLine(getItem(position).getOnline()));
        if (getItem(position).getCate_id() == 201) {
            holder.rlLiveType.setBackgroundResource(R.drawable.search_header_live_type_mobile);
        }
    }

    @Override
    public int getItemCount() {
        return bigDataRoomList.size();
    }

    private BigDataRoomBean.DataBean getItem(int position) {
        return bigDataRoomList.get(position);
    }

    private String getOnLine(int number) {
        DecimalFormat df = new DecimalFormat(".#");
        if (number < 10000) {
            return number + "";
        } else {
            double nums = (double) number / 10000;
            String result = df.format(nums);
            return result + "万";
        }
    }

    /**
     * 点击某个item的监听接口
     */
    public interface OnItemClickListener {
        /**
         * 某个item被点击的回调
         *
         * @param view     view
         * @param position position
         */
        void OnItemClick(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView simpleDraweeView;
        TextView tvName, tvOnlineNum, tvNickname;
        RelativeLayout rlLiveType;

        public ViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.simpleDraweeView);
            tvName = itemView.findViewById(R.id.tvName);
            tvOnlineNum = itemView.findViewById(R.id.tvOnlineNum);
            tvNickname = itemView.findViewById(R.id.tvNickname);
            rlLiveType = itemView.findViewById(R.id.rlLiveType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(view, getLayoutPosition());
                    }

                }
            });
        }
    }
}
