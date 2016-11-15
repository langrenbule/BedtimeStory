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
import com.deity.bedtimestory.dao.NewBornContentEntity;
import com.deity.bedtimestory.dao.NewBornItemEntity;
import com.deity.bedtimestory.dao.NewBronContent;
import com.deity.bedtimestory.entity.NewBornContentType;
import com.deity.bedtimestory.network.DataBiz;
import com.deity.bedtimestory.network.TechBabyBiz;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
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
    private List<NewBornContentEntity> mDatas;
    @Bind(R.id.backdrop) public ImageView backdrop;
    private static final String TAG = NewsContentActivity.class.getSimpleName();
    private Subscription subscription;

    /**
     * 该页面的url
     */
    private String url;
    private DataBiz<NewBornItemEntity,NewBronContent> mNewItemBiz;

    private NewContentAdapter mAdapter;
    private SwipeRefreshLayout refresh_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsContentActivity.this.finish();
            }
        });
        refresh_layout = (SwipeRefreshLayout) this.findViewById(R.id.refresh_layout);
        refresh_layout.setOnRefreshListener(this);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mNewItemBiz = TechBabyBiz.getInstance();
        Bundle extras = getIntent().getExtras();
        collapsingToolbar.setTitle(extras.getString("newsTitle"));
        url = extras.getString("url");
        String imageUrl = extras.getString("imageUrl");
        if(!TextUtils.isEmpty(imageUrl)){
            Glide.with(this).load(imageUrl).placeholder(R.drawable.ic_launcher).into(backdrop);
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void getNewItems(final String destUrl){
        subscription = Observable.create(new Observable.OnSubscribe<List<NewBornContentEntity>>() {
            @Override
            public void call(Subscriber<? super List<NewBornContentEntity>> subscriber) {
                try {
                    mDatas = mNewItemBiz.getArticleContents(destUrl).getNewBornContentEntities();
                }catch (Exception e){
                    Log.i(TAG,"ERROR"+e.getMessage());
                }
                subscriber.onNext(mDatas);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    Subscriber<List<NewBornContentEntity>> subscriber = new Subscriber<List<NewBornContentEntity>>() {
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
        public void onNext(List<NewBornContentEntity> newItems) {
            Log.i(TAG,"onNext");
            if (null==newItems||newItems.isEmpty()){
                newItems = new ArrayList<>();
                NewBornContentEntity news = new NewBornContentEntity();
                news.setNewBornType(NewBornContentType.NEW_BORN_CONTENT_TYPE_CONTENT.getCode());
                news.setNewBornContent("加载失败，请下拉刷新重新加载！");
                newItems.add(news);
            }
            mDatas = newItems;
            mAdapter.setData(mDatas);
            mAdapter.notifyDataSetChanged();
            refresh_layout.setRefreshing(false);
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
