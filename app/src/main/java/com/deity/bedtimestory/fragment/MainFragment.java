package com.deity.bedtimestory.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.deity.bedtimestory.NewsContentActivity;
import com.deity.bedtimestory.R;
import com.deity.bedtimestory.adapter.NewItemsAdapter;
import com.deity.bedtimestory.data.Params;
import com.deity.bedtimestory.entity.NewItem;
import com.deity.bedtimestory.network.NewItemBiz;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment通用
 * Created by fengwenhua on 2016/4/12.
 */
public class MainFragment extends Fragment{
    private String targetType;

    @Bind(R.id.content_items)
    public PullToRefreshListView content_items;
    @Bind(R.id.reLoadImage)
    public ImageView reloadImag;
    private NewItemBiz mNewItemBiz;
    /**
     * 数据
     */
    private List<NewItem> mDatas = new ArrayList<NewItem>();
    /**
     * 数据适配器
     */
    private NewItemsAdapter mAdapter;
    /**
     * 当前页面
     */
    private int currentPage = 1;
    /**
     * 是否是第一次进入
     */
    private boolean isFirstIn = true;

    public MainFragment(){
        mNewItemBiz = new NewItemBiz();
    }

    @SuppressLint("ValidFragment")
    public MainFragment(String targetType){
        this.targetType = targetType;
        mNewItemBiz = new NewItemBiz();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
//        ButterKnife.bind(getActivity());
        content_items = (PullToRefreshListView) view.findViewById(R.id.content_items);
        reloadImag = (ImageView) view.findViewById(R.id.reLoadImage);
        reloadImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content_items.setRefreshing(true);
            }
        });
        // Set a listener to be invoked when the list should be refreshed.
        content_items.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDatasTask().execute(Params.LOAD_REFRESH, targetType);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDatasTask().execute(Params.LOAD_MORE, targetType);
            }
        });
        content_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewItem newsItem = mDatas.get(position - 1);
                Intent intent = new Intent(getActivity(), NewsContentActivity.class);
                intent.putExtra("url", newsItem.getLink());
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new NewItemsAdapter(getActivity());
        mAdapter.setData(mDatas);
        content_items.setAdapter(mAdapter);
        if (isFirstIn){
            isFirstIn=false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    content_items.setRefreshing(true);
                }
            },1000);
        }else{
            if(null==mDatas||mDatas.size()<=0){
                reloadImag.setVisibility(View.VISIBLE);
            }
        }
    }


    /**
     * 下拉刷新数据
     */
    public Integer refreashData(String targetUrl){
        // 获取最新数据
        try{
            List<NewItem> newsItems = mNewItemBiz.getArticleItems(targetUrl, currentPage);
            mDatas.addAll(newsItems);
            mAdapter.setData(newsItems);
        } catch (Exception e) {

            e.printStackTrace();
            return -1;
        }
        return -1;

    }


    /**
     * 会根据当前网络情况，判断是从数据库加载还是从网络继续获取
     */
    public void loadMoreData(String targetUrl){
        // 当前数据是从网络获取的
        currentPage += 1;
        try{
            List<NewItem> newsItems = mNewItemBiz.getArticleItems(targetUrl, currentPage);
            mDatas.addAll(newsItems);
            mAdapter.addAll(newsItems);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 记载数据的异步任务
     *
     * @author zhy
     *
     */
    class LoadDatasTask extends AsyncTask<String, Void, Integer>
    {

        @Override
        protected Integer doInBackground(String... params) {
            switch (params[0]) {
                case Params.LOAD_REFRESH:
                    loadMoreData(targetType);
                    break;
                case Params.LOAD_MORE:
                    return refreashData(targetType);
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            switch (result) {
                default:
                    if (null==mDatas||mDatas.isEmpty()){
                        reloadImag.setVisibility(View.VISIBLE);
                    }else{
                        reloadImag.setVisibility(View.GONE);
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
            }
            content_items.onRefreshComplete();
        }

    }
}
