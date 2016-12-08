package com.tik.testa1.adapter;

import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tik.testa1.R;

import java.util.List;

public class FocusListViewAdapter extends BaseAdapter {

    private List<String> mData;
    private Context mContext;
    private int mCurrentItem = 0;

    public FocusListViewAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
    }

    public int getCount() {
        return mData.size();
    }

    public Object getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
//        return mCurrentItem == position ? 1 : 0;
        return position%2;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder1 = null;
        ViewHolder holder2 = null;
        int type = getItemViewType(position);
        if(convertView == null){
            if(type == 1){
                holder1 = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.focus_item1, null);
                holder1.iv = (ImageView) convertView.findViewById(R.id.iv);
                holder1.tv = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(holder1);
            }else{
                holder2 = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.focus_item2, null);
                holder2.iv = (ImageView) convertView.findViewById(R.id.iv);
                holder2.tv = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(holder2);
            }

        }else{
            if(type == 1){
                holder1 = (ViewHolder) convertView.getTag();
            }else{
                holder2 = (ViewHolder) convertView.getTag();
            }

        }
        if(type == 1){
            holder1.iv.setImageResource(R.mipmap.ic_launcher);
            holder1.tv.setText(mData.get(position).toString());
        }else{
            holder2.iv.setImageResource(R.mipmap.ic_launcher);
            holder2.tv.setText(mData.get(position).toString());
        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        TextView tv;
    }

    public void setCurrentItem(int currentItem) {
        this.mCurrentItem = currentItem;
    }

}