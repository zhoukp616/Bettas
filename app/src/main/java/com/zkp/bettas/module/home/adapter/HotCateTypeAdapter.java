package com.zkp.bettas.module.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zkp.bettas.R;
import com.zkp.bettas.module.home.bean.CateTypeBean;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.adapter
 * @time: 2018/8/16 8:08
 * @description:
 */
public class HotCateTypeAdapter extends RecyclerView.Adapter<HotCateTypeAdapter.ViewHolder> {

    private Context context;
    private CateTypeBean cateTypeBean;

    private OnItemClickListener onItemClickListener;

    public HotCateTypeAdapter(Context context, CateTypeBean cateTypeBean) {
        this.context = context;
        this.cateTypeBean = cateTypeBean;
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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cate_type, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(getItem(position).getIcon_url()).into(holder.ivIcon);
        holder.tvType.setText(getItem(position).getTag_name());
    }

    @Override
    public int getItemCount() {
        return cateTypeBean.getData().size();
    }

    public CateTypeBean.DataBean getItem(int position) {
        return cateTypeBean.getData().get(position);
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

        CircleImageView ivIcon;
        TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvType = itemView.findViewById(R.id.tvType);

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
