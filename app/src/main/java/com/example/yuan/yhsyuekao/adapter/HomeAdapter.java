package com.example.yuan.yhsyuekao.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuan.yhsyuekao.Bean.UserBean;
import com.example.yuan.yhsyuekao.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends BaseAdapter {
    private Context mContext;
    private List<UserBean.DataBean> mDatas;

    public HomeAdapter(Context mContext) {
        this.mContext = mContext;
        mDatas=new ArrayList<>();
    }
    //刷新


    public void setmDatas(List<UserBean.DataBean> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }
    //加载
    public void addmDatas(List<UserBean.DataBean> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null)
        {
            convertView=LayoutInflater.from(mContext).inflate(R.layout.pulllistview,parent,false);
            holder=new ViewHolder();
            holder.title=convertView.findViewById(R.id.title);
            holder.ima=convertView.findViewById(R.id.ima);
            convertView.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) convertView.getTag();

        }
        holder.title.setText(mDatas.get(position).getTitle());
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.default_ptr_flip)
                .showImageOnFail(R.drawable.default_ptr_flip)
                .showImageOnLoading(R.drawable.default_ptr_flip)
                .build();
        ImageLoader.getInstance().displayImage(mDatas.get(position).getThumbnail_pic_s(),holder.ima,options);
        return convertView;
    }

    class ViewHolder{
        TextView title;
        ImageView ima;
    }
}
