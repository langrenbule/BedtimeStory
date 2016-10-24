package com.deity.bedtimestory.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.deity.bedtimestory.NewsContentActivity;
import com.deity.bedtimestory.R;
import com.deity.bedtimestory.adapter.HeaderViewRecyclerAdapter;
import com.deity.bedtimestory.adapter.NewItemsAdapter;
import com.deity.bedtimestory.dao.NewItemDaoImpl;
import com.deity.bedtimestory.dao.NewItemEntity;
import com.deity.bedtimestory.data.Params;
import com.deity.bedtimestory.entity.NewItem;
import com.deity.bedtimestory.event.NetWorkEvent;
import com.deity.bedtimestory.event.UIEvent;
import com.deity.bedtimestory.network.NewItemBiz;
import com.deity.bedtimestory.utils.EndlessRecyclerOnScrollListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

import static com.deity.bedtimestory.event.NetWorkEvent.REQUEST_NETWORK_DATA;

/**
 * Fragment通用
 * Created by fengwenhua on 2016/4/12.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private String targetType;
    private int newsType;

    public RecyclerView content_items;
    /**RefreshLayout*/
    public SwipeRefreshLayout refreshLayout;
    public ImageView reloadImag;
    private NewItemBiz mNewItemBiz;
    HeaderViewRecyclerAdapter headerViewRecyclerAdapter;
    /**
     * 数据
     */
    private RealmResults<NewItemEntity> mDatas;
    /**
     * 数据适配器
     */
    private NewItemsAdapter mAdapter;
    /**
     * 当前页面
     */
    private int currentPage = 1;

    public MainFragment(){
        mNewItemBiz = new NewItemBiz();
    }

    @SuppressLint("ValidFragment")
    public MainFragment(Params.NewType newsType){
        this.targetType = newsType.getDestUrl();
        this.newsType = newsType.getCode();
        mNewItemBiz = new NewItemBiz();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
//        ButterKnife.bind(this,view);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        content_items = (RecyclerView) view.findViewById(R.id.content_items);
        reloadImag = (ImageView) view.findViewById(R.id.reLoadImage);
        reloadImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.setRefreshing(true);
            }
        });
        refreshLayout.setOnRefreshListener(this);
        mAdapter = new NewItemsAdapter(getActivity());
        headerViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        content_items.setAdapter(headerViewRecyclerAdapter);
        mDatas = NewItemDaoImpl.instance.queryNewItemEntities(newsType);
        mDatas.addChangeListener(new RealmChangeListener<RealmResults<NewItemEntity>>() {
            @Override
            public void onChange(RealmResults<NewItemEntity> element) {
                mAdapter.setData(mDatas);
                mAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
        mAdapter.setRecycleViewOnClickListener(new NewItemsAdapter.RecycleViewOnClickListener() {
            @Override
            public void onItemClick(View view, NewItemEntity data) {
                Intent intent = new Intent(getActivity(), NewsContentActivity.class);
                intent.putExtra("url", data.getLink());
                intent.putExtra("imageUrl", data.getImgLink());
                startActivity(intent);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        },1000);
        NetWorkEvent event = REQUEST_NETWORK_DATA;
        event.setData(targetType,currentPage);
        EventBus.getDefault().post(event);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        content_items.setHasFixedSize(true);
        content_items.setLayoutManager(linearLayoutManager);
        content_items.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                NetWorkEvent event = REQUEST_NETWORK_DATA;
                event.setData(targetType,currentPage);
                EventBus.getDefault().post(event);
            }
        });
        return view;
    }

    /**
     * 请求网络数据
     */
    public void requestNetWorkData(String targetUrl,int currentPage){
        try {
            List<NewItem> newsItems = mNewItemBiz.getArticleItems(targetUrl, currentPage);
            if (null!=newsItems){
                NewItemDaoImpl.instance.addNewItemEntities(newsItems);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("request Exception>>>"+e.getMessage());
        }
        UIEvent event = UIEvent.UI_REFRESH_OVER;
        EventBus.getDefault().post(event);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        NetWorkEvent event = REQUEST_NETWORK_DATA;
        event.setData(targetType,currentPage);
        EventBus.getDefault().post(event);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventNetWork(NetWorkEvent event){
        switch (event){
            case REQUEST_NETWORK_DATA:
                int currentPage =event.getCurrentPage();
                String destUrl = event.getDestUrl();
                requestNetWorkData(destUrl,currentPage);
                break;
        }
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUIEvent(UIEvent event){
        switch (event){
            case UI_REFRESH_OVER:
                refreshLayout.setRefreshing(false);
                break;
        }
    }
}
