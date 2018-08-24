package com.zkp.bettas.module.home.other.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zkp.bettas.R;
import com.zkp.bettas.module.home.other.bean.OtherBean;

import java.util.List;

/**
 * @author: zhoukp
 * @project: Bettas
 * @package: com.zkp.bettas.module.home.other.adapter
 * @time: 2018/8/22 9:34
 * @description:
 */
public class GridAdapter extends BaseAdapter {

    private List<OtherBean.DataBean> data;
    private LayoutInflater mInflater;
    private Context context;
    //页数下标
    private int mIndex;
    //每页显示多少
    private int mPagerSize;

    public GridAdapter(Context context, List<OtherBean.DataBean> data, int i, int mPageSize) {
        this.context = context;
        this.data = data;
        this.mIndex = i;
        this.mPagerSize = mPageSize;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size() > (mIndex + 1) * mPagerSize ? mPagerSize : (data.size() - mIndex * mPagerSize);
    }

    @Override
    public OtherBean.DataBean getItem(int i) {
        return data.get(i + mIndex * mPagerSize);
    }

    @Override
    public long getItemId(int i) {
        return i + mIndex * mPagerSize;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_gridview, viewGroup, false);
            holder = new ViewHolder();
            holder.tvName = view.findViewById(R.id.tvName);
            holder.itemGrid = view.findViewById(R.id.itemGrid);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (getItemId(i) == data.size() - 1) {
            holder.tvName.setText("全部分类");
            holder.itemGrid.setImageURI(Uri.parse("res://com.zkp.battas/" + R.drawable.more_icon));
        } else {
            holder.tvName.setText(getItem(i).getTag_name());
            holder.itemGrid.setImageURI(Uri.parse(getItem(i).getIcon_url().replace("https://staticlive.douyucdn.cn/upload/game_cate/", "")));
        }

        return view;
    }

    static class ViewHolder {
        TextView tvName;
        SimpleDraweeView itemGrid;
    }
}
