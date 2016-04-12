package com.deity.bedtimestory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deity.bedtimestory.R;
import com.deity.bedtimestory.entity.NewItem;
import com.deity.bedtimestory.utils.SmallUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by fengwenhua on 2016/4/12.
 */
public class NewItemsAdapter extends BaseAdapter{

    private List<NewItem> newItems;
    private LayoutInflater inflater;

    public NewItemsAdapter(Context context){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (null==newItems||newItems.isEmpty()){
            return 0;
        }
        return newItems.size();

    }

    @Override
    public Object getItem(int position) {
        return newItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null==convertView){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_news,null);
            viewHolder.mContent = (TextView) convertView.findViewById(R.id.id_content);
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.id_title);
            viewHolder.mDate = (TextView) convertView.findViewById(R.id.id_date);
            viewHolder.mImg = (ImageView) convertView.findViewById(R.id.id_newsImg);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //-------------------------------------
        NewItem newsItem = newItems.get(position);
        viewHolder.mTitle.setText(newsItem.getTitle());//TODO 原有更改
        viewHolder.mContent.setText(newsItem.getContent());
        viewHolder.mDate.setText(newsItem.getDate());
        if (newsItem.getImgLink() != null) {
            viewHolder.mImg.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(newsItem.getImgLink(), viewHolder.mImg, SmallUtils.imageOptions());
        } else {
            viewHolder.mImg.setVisibility(View.GONE);
        }
        return convertView;
    }

    public class ViewHolder{
        public TextView mTitle;
        public TextView mContent;
        public ImageView mImg;
        public TextView mDate;
    }
}
