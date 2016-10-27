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
import android.util.Log;
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
import com.deity.bedtimestory.network.NewItemBiz;
import com.deity.bedtimestory.utils.EndlessRecyclerOnScrollListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.deity.bedtimestory.event.NetWorkEvent.REQUEST_NETWORK_DATA;

/**
 * Fragment通用
 * Created by fengwenhua on 2016/4/12.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = MainFragment.class.getSimpleName();
    private String targetType;
    private int newsType;
    private Subscription subscription;

    public RecyclerView content_items;
    /**
     * RefreshLayout
     */
    public SwipeRefreshLayout refreshLayout;
    public ImageView reloadImag;
    private NewItemBiz mNewItemBiz;
    HeaderViewRecyclerAdapter headerViewRecyclerAdapter;
    /**
     * 数据
     */
    private List<NewItemEntity> mDatas;
    private List<NewItemEntity> mTotalDatas = new ArrayList<>();
    /**
     * 数据适配器
     */
    private NewItemsAdapter mAdapter;
    /**
     * 当前页面
     */
    public int currentNewsPage = 1;

    public MainFragment() {
        mNewItemBiz = new NewItemBiz();
    }

    @SuppressLint("ValidFragment")
    public MainFragment(Params.NewType newsType) {
        this.targetType = newsType.getDestUrl();
        this.newsType = newsType.getCode();
        mNewItemBiz = new NewItemBiz();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
//        ButterKnife.bind(this,view);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        content_items = (RecyclerView) view.findViewById(R.id.content_items);
        reloadImag = (ImageView) view.findViewById(R.id.reLoadImage);
        reloadImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.setRefreshing(true);
                getNewItems(targetType, currentNewsPage);
            }
        });
        refreshLayout.setOnRefreshListener(this);
        mAdapter = new NewItemsAdapter(getActivity());
        headerViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        content_items.setAdapter(headerViewRecyclerAdapter);

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
                getNewItems(targetType, currentNewsPage);
            }
        }, 1000);
        NetWorkEvent event = REQUEST_NETWORK_DATA;
        event.setData(targetType, currentNewsPage);
        EventBus.getDefault().post(event);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        content_items.setHasFixedSize(true);
        content_items.setLayoutManager(linearLayoutManager);
        createLoadMoreView();
        content_items.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                System.out.println("加载更多中...");
                headerViewRecyclerAdapter.setFooterViewsVisable();
                currentNewsPage = currentPage;
                getNewItems(targetType,currentPage);

            }
        });
        return view;
    }

    private void createLoadMoreView() {
        View loadMoreView = LayoutInflater
                .from(getActivity())
                .inflate(R.layout.load_more_view, content_items, false);
        headerViewRecyclerAdapter.addFooterView(loadMoreView);
    }

    /**
     * 请求网络数据
     */
    public List<NewItem> requestNetWorkData(String targetUrl, int currentPage) {
        List<NewItem> newsItems = null;
        try {
            newsItems = mNewItemBiz.getArticleItems(targetUrl, currentPage);
            if (null != newsItems) {
                NewItemDaoImpl.instance.addNewItemEntities(newsItems);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("request Exception>>>" + e.getMessage());
        }
        return newsItems;
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unsubscribe();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        getNewItems(targetType, currentNewsPage);
    }

    public void getNewItems(final String destUrl, final int currentPage){
        subscription = Observable.create(new Observable.OnSubscribe<List<NewItem>>() {
            @Override
            public void call(Subscriber<? super List<NewItem>> subscriber) {
                List<NewItem> newItems = requestNetWorkData(destUrl, currentPage);
                subscriber.onNext(newItems);
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    Subscriber<List<NewItem>> subscriber = new Subscriber<List<NewItem>>() {
        @Override
        public void onCompleted() {
            Log.i(TAG,"OK");
//            mDatas = NewItemDaoImpl.instance.queryNewItemEntities(newsType,(currentNewsPage-1));
//            updateUI();
        }

        @Override
        public void onError(Throwable e) {
            Log.i(TAG,"ERROR");
//            mDatas = NewItemDaoImpl.instance.queryNewItemEntities(newsType,(currentNewsPage-1));
//            updateUI();
        }

        @Override
        public void onNext(List<NewItem> newItems) {
            NewItemDaoImpl.instance.addNewItemEntities(newItems);
            mDatas = NewItemDaoImpl.instance.queryNewItemEntities(newsType,(currentNewsPage-1));
            updateUI(mDatas);
            //如果成功获取到，那么就直接显示呗
            Log.i(TAG,"onNext");
        }
    };

    public void updateUI(List<NewItemEntity> mDatas){
        mTotalDatas.addAll(mDatas);
        if (mTotalDatas.isEmpty()){
            reloadImag.setVisibility(View.VISIBLE);
        }else {
            reloadImag.setVisibility(View.GONE);
        }
        mAdapter.setData(mTotalDatas);
        mAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
        headerViewRecyclerAdapter.notifyDataSetChanged();
    }
}
