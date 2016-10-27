package com.deity.bedtimestory.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deity.bedtimestory.R;
import com.deity.bedtimestory.entity.News;
import com.deity.bedtimestory.entity.News.NewsType;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;


public class NewContentAdapter extends RecyclerView.Adapter<NewContentAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<News> mDatas = new ArrayList<News>();

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

    public void setData(List<News> datas) {
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
            case NewsType.TITLE:
                convertView = mInflater.inflate(R.layout.news_content_title_item, null);
                break;
            case NewsType.SUMMARY:
                convertView = mInflater.inflate(R.layout.news_content_summary_item, null);
                break;
            case NewsType.CONTENT:
                convertView = mInflater.inflate(R.layout.news_content_item, null);
                break;
            case NewsType.IMG:
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news = mDatas.get(position);
        switch (news.getType()){
            case NewsType.IMG:
//                imageLoader.displayImage(news.getImageLink(), holder.mImageView, options);
                break;
            case NewsType.TITLE:
//                holder.mTextView.setText(news.getTitle());
                break;
            case NewsType.SUMMARY:
                holder.mTextView.setText(news.getSummary());
                break;
            case NewsType.CONTENT:
                holder.mTextView.setText(Html.fromHtml(news.getContent()));
                break;
            case NewsType.BOLD_TITLE:
                holder.mTextView.setText(Html.fromHtml(news.getContent()));
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (mDatas.get(position).getType()) {
            case NewsType.TITLE:
                return 0;
            case NewsType.SUMMARY:
                return 1;
            case NewsType.CONTENT:
                return 2;
            case NewsType.IMG:
                return 3;
            case NewsType.BOLD_TITLE:
                return 4;
        }
        return -1;
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
