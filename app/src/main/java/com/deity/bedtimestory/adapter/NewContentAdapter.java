package com.deity.bedtimestory.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deity.bedtimestory.R;
import com.deity.bedtimestory.dao.NewBornContentEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;


public class NewContentAdapter extends RecyclerView.Adapter<NewContentAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<NewBornContentEntity> mDatas = new ArrayList<>();

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    public NewContentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder().showStubImage(R.drawable.images)
                .showImageForEmptyUri(R.drawable.images).showImageOnFail(R.drawable.images).cacheInMemory()
                .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300)).build();
    }

    public void setData(List<NewBornContentEntity> datas) {
        mDatas = datas;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (null==mDatas)return 0;
        return mDatas.size();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = null;
        switch (viewType){
            case 1://NewBornContentType.NEW_BORN_CONTENT_TYPE_DESCRIPTION
                convertView = mInflater.inflate(R.layout.news_content_title_item, null);
                break;
            case 2://NewBornContentType.NEW_BORN_CONTENT_TYPE_SUMMARY
                convertView = mInflater.inflate(R.layout.news_content_summary_item, null);
                break;
            case 3://NewBornContentType.NEW_BORN_CONTENT_TYPE_CONTENT
                convertView = mInflater.inflate(R.layout.news_content_item, null);
                break;
            case 0://NewBornContentType.NEW_BORN_CONTENT_IMAGEURL
                convertView = mInflater.inflate(R.layout.news_content_img_item, null);
                break;
            default://NewsType.BOLD_TITLE
                convertView = mInflater.inflate(R.layout.news_content_bold_title_item, null);
                break;
        }
        //将创建的View注册点击事件
        return new NewContentAdapter.ViewHolder(convertView);
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getNewBornType();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewBornContentEntity news = mDatas.get(position);
        if (TextUtils.isEmpty(news.getNewBornContent())) return;
        switch (news.getNewBornType()){
            case 0: //NewBornContentType.NEW_BORN_CONTENT_IMAGEURL.getCode()
                imageLoader.displayImage(news.getNewBornContent(), holder.mImageView, options);
                break;
            case 1://NewBornContentType.NEW_BORN_CONTENT_TYPE_TITLE
                holder.mTextView.setText(news.getNewBornContent());
                break;
            case 2://NewBornContentType.NEW_BORN_CONTENT_TYPE_SUMMARY
                holder.mTextView.setText(news.getNewBornContent());
                break;
            case 3://NewBornContentType.NEW_BORN_CONTENT_TYPE_CONTENT
                holder.mTextView.setText(news.getNewBornContent());
                break;
            case 4://NewBornContentType.NEW_BORN_CONTENT_TYPE_TITLE
                holder.mTextView.setText(news.getNewBornContent());
            default:
                holder.mTextView.setText(news.getNewBornContent());
                break;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
