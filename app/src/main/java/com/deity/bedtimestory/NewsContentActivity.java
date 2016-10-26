package com.deity.bedtimestory;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deity.bedtimestory.adapter.NewContentAdapter;
import com.deity.bedtimestory.entity.News;
import com.deity.bedtimestory.network.NewItemBiz;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.deity.bedtimestory.R.id.content_items;


public class NewsContentActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mListView;
    private List<News> mDatas;
    @Bind(R.id.backdrop) public ImageView backdrop;
    private static final String TAG = NewsContentActivity.class.getSimpleName();
    private Subscription subscription;

    /**
     * 该页面的url
     */
    private String url;
    private NewItemBiz mNewItemBiz;

    private NewContentAdapter mAdapter;
    private SwipeRefreshLayout refresh_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        ButterKnife.bind(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        refresh_layout = (SwipeRefreshLayout) this.findViewById(R.id.refresh_layout);
        refresh_layout.setOnRefreshListener(this);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("这是标题");
        mNewItemBiz = new NewItemBiz();

        Bundle extras = getIntent().getExtras();
        url = extras.getString("url");
        System.out.println("读取的地址:"+url);


        String imageUrl = extras.getString("imageUrl");
        if(!TextUtils.isEmpty(imageUrl)){
            Glide.with(this).load(imageUrl).into(backdrop);
        }else {
            Glide.with(this).load(R.drawable.ic_launcher).into(backdrop);
        }
        mAdapter = new NewContentAdapter(this);
        mListView = (RecyclerView) findViewById(content_items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewsContentActivity.this);
        mListView.setHasFixedSize(true);
        mListView.setLayoutManager(linearLayoutManager);
        mListView.setAdapter(mAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh_layout.setRefreshing(true);
                getNewItems(url);
            }
        }, 1000);

    }

    @Override
    public void onRefresh() {
        refresh_layout.setRefreshing(true);
        getNewItems(url);
    }

    public void getNewItems(final String destUrl){
        subscription = Observable.create(new Observable.OnSubscribe<List<News>>() {
            @Override
            public void call(Subscriber<? super List<News>> subscriber) {
                try {
                    mDatas = mNewItemBiz.getNews(destUrl).getNewses();
                }catch (Exception e){
                    Log.i(TAG,"ERROR"+e.getMessage());
                }
                subscriber.onNext(mDatas);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    Subscriber<List<News>> subscriber = new Subscriber<List<News>>() {
        @Override
        public void onCompleted() {
            Log.i(TAG,"OK");
            mAdapter.notifyDataSetChanged();
            refresh_layout.setRefreshing(false);
        }

        @Override
        public void onError(Throwable e) {
            Log.i(TAG,"ERROR");
            mAdapter.notifyDataSetChanged();
            refresh_layout.setRefreshing(false);
        }

        @Override
        public void onNext(List<News> newItems) {
            Log.i(TAG,"onNext");
            try {
                mDatas = newItems;
                mAdapter.setData(mDatas);
            } catch (Exception e) {
                //
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void back(View view) {
        finish();
    }

}
