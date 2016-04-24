package com.deity.bedtimestory;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;

import com.deity.bedtimestory.adapter.NewContentAdapter;
import com.deity.bedtimestory.data.MyApplication;
import com.deity.bedtimestory.entity.News;
import com.deity.bedtimestory.network.NewItemBiz;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;


public class NewsContentActivity extends Activity {

    private PullToRefreshListView mListView;
    private List<News> mDatas;

    /**
     * 该页面的url
     */
    private String url;
    private NewItemBiz mNewItemBiz;

    private ProgressBar mProgressBar;
    private NewContentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);

        mNewItemBiz = new NewItemBiz();

        Bundle extras = getIntent().getExtras();
        url = extras.getString("url");
        System.out.println("读取的地址:"+url);
        mAdapter = new NewContentAdapter(this);

        mListView = (PullToRefreshListView) findViewById(R.id.content_items);
        mProgressBar = (ProgressBar) findViewById(R.id.id_newsContentPro);

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//				News news = mDatas.get(position - 1);
//				String imageLink = news.getImageLink();
//				//Toast.makeText(NewContentActivity.this, imageLink, 1).show();
//				Intent intent = new Intent(NewsContentActivity.this,ImageShowActivity.class);
//				intent.putExtra("url", imageLink);
//				startActivity(intent);
            }
        });
        mProgressBar.setVisibility(View.VISIBLE);
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
            mProgressBar.setVisibility(View.GONE);
        }

    }

    public void back(View view) {
        finish();
    }

}
