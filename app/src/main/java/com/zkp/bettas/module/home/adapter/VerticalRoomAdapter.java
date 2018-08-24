package com.zkp.bettas.module.home.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zkp.bettas.R;
import com.zkp.bettas.module.home.bean.VerticalRoomBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.adapter
 * @time: 2018/8/17 10:47
 * @description:
 */
public class VerticalRoomAdapter extends RecyclerView.Adapter<VerticalRoomAdapter.ViewHolder> {

    private Context context;
    private List<VerticalRoomBean.DataBean> verticalRoomList;

    private OnItemClickListener onItemClickListener;

    public VerticalRoomAdapter(Context context, List<VerticalRoomBean.DataBean> verticalRoomList) {
        this.context = context;
        this.verticalRoomList = verticalRoomList;
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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_vertical_room, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.simpleDraweeView.setImageURI(Uri.parse(getItem(position).getVertical_src()));
        holder.tvNickname.setText(getItem(position).getNickname());
        holder.tvOnlineNum.setText(getOnLine(getItem(position).getOnline()));
        holder.tvCity.setText(getItem(position).getAnchor_city());
    }

    @Override
    public int getItemCount() {
        return verticalRoomList.size();
    }

    private VerticalRoomBean.DataBean getItem(int position) {
        return verticalRoomList.get(position);
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
        TextView tvNickname, tvCity, tvOnlineNum;

        public ViewHolder(View itemView) {
            super(itemView);

            simpleDraweeView = itemView.findViewById(R.id.simpleDraweeView);
            tvNickname = itemView.findViewById(R.id.tvNickname);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvOnlineNum = itemView.findViewById(R.id.tvOnlineNum);

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
