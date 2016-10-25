package com.deity.bedtimestory;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.deity.bedtimestory.adapter.NewContentAdapter;
import com.deity.bedtimestory.entity.News;
import com.deity.bedtimestory.network.NewItemBiz;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.deity.bedtimestory.R.id.content_items;


public class NewsContentActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mListView;
    private List<News> mDatas;
    @Bind(R.id.backdrop) public ImageView backdrop;

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
        new LoadDataTask().execute();
    }

    @Override
    public void onRefresh() {
        refresh_layout.setRefreshing(true);
        new LoadDataTask().execute();
    }

    class LoadDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                mDatas = mNewItemBiz.getNews(url).getNewses();
            } catch (Exception e) {
                //
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (mDatas == null)
                return;
            mAdapter.addList(mDatas);
            mAdapter.notifyDataSetChanged();
            refresh_layout.setRefreshing(false);
        }

    }

    public void back(View view) {
        finish();
    }

}
