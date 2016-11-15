package com.deity.bedtimestory.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deity.bedtimestory.R;
import com.deity.bedtimestory.dao.NewItemEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengwenhua on 2016/4/12.
 */
public class NewItemsAdapter extends RecyclerView.Adapter<NewItemsAdapter.ViewHolder> implements  View.OnClickListener {

    private List<NewItemEntity> newItems = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private RecycleViewOnClickListener recycleViewOnClickListener;
    private DisplayImageOptions options;

    public NewItemsAdapter(Context context){
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.images)
                .showImageForEmptyUri(R.drawable.images).showImageOnFail(R.drawable.images).cacheInMemory()
                .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300)).build();
    }

    @Override
    public void onClick(View view) {
        if (null!=recycleViewOnClickListener){
            recycleViewOnClickListener.onItemClick(view, (NewItemEntity) view.getTag());
        }
    }

    public interface RecycleViewOnClickListener{
        void onItemClick(View view,NewItemEntity data);
    }

    public void setRecycleViewOnClickListener(RecycleViewOnClickListener recycleViewOnClickListener) {
        this.recycleViewOnClickListener = recycleViewOnClickListener;
    }

    public void setData(List<NewItemEntity> newItems){
        this.newItems = newItems;
    }

    public void addAll(List<NewItemEntity> mDatas){
        if (null!=mDatas) {
            this.newItems.addAll(mDatas);
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_news, parent, false);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return new NewItemsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewItemEntity data = newItems.get(position);
        holder.mTitle.setText(data.getTitle());
        holder.mContent.setText(data.getContent());
//        Glide.with(context).load(data.getImgLink()).placeholder(R.drawable.ic_launcher).into(holder.mImg);
        ImageLoader.getInstance().displayImage(data.getImgLink(), holder.mImg, options);
        holder.mDate.setText(data.getDate());
        holder.itemView.setTag(data);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (null==newItems) return 0;
        return newItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitle;
        public TextView mContent;
        public ImageView mImg;
        public TextView mDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.id_title);
            mContent = (TextView) itemView.findViewById(R.id.id_content);
            mImg = (ImageView) itemView.findViewById(R.id.id_newsImg);
            mDate = (TextView) itemView.findViewById(R.id.id_date);
        }
    }
}
