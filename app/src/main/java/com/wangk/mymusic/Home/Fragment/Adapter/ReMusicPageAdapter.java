package com.wangk.mymusic.Home.Fragment.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wangk.mymusic.Home.Fragment.Bean.Result;
import com.wangk.mymusic.R;

import java.util.List;

public class ReMusicPageAdapter extends BaseAdapter {
    private List<Result> mData;
    private Context mContext;

    public ReMusicPageAdapter(List<Result> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.sheet_item,parent,false);
        ImageView img_icon = (ImageView) convertView.findViewById(R.id.fruit_image);
        TextView txt_aName = (TextView) convertView.findViewById(R.id.fruit_name);
        //加载网络图片
        //img_icon.setBackgroundResource(mData.get(position).getaIcon());

        //.error( R.drawable.error) //异常时候显示的图片
        Glide.with(mContext)
                .load(mData.get(position).getPicUrl())
                .into(img_icon);//在RequestBuilder 中使用自定义的ImageViewTarget

        txt_aName.setText(mData.get(position).getName());
        return convertView;
    }
}
