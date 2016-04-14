package com.deity.bedtimestory.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.deity.bedtimestory.R;
import com.deity.bedtimestory.adapter.NewItemsAdapter;
import com.deity.bedtimestory.data.Params;
import com.deity.bedtimestory.entity.NewItem;
import com.deity.bedtimestory.network.NewItemBiz;
import com.deity.bedtimestory.widget.NewDataToast;
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
    private int type;

    @Bind(R.id.content_items)
    public PullToRefreshListView content_items;
    private NewItemBiz mNewItemBiz;
    private static final int LOAD_MORE = 0x110;
    private static final int LOAD_REFREASH = 0x111;
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

    public MainFragment(){
        mNewItemBiz = new NewItemBiz();
    }

    public MainFragment(int type){
        this.type = type;
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
        // Set a listener to be invoked when the list should be refreshed.
        content_items.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDatasTask().execute(LOAD_REFREASH);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new LoadDatasTask().execute(LOAD_MORE);
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
        content_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewDataToast.makeText(getActivity(),"用户点击了Item").show();
//                NewsItem newsItem = mDatas.get(position-1);
//                Intent intent = new Intent(getActivity(), NewsContentActivity.class);
//                intent.putExtra("url", newsItem.getLink());
//                startActivity(intent);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                content_items.setRefreshing();
            }
        },1000);

    }


    /**
     * 下拉刷新数据
     */
    public Integer refreashData()
    {
        // 获取最新数据
        try{
            List<NewItem> newsItems = mNewItemBiz.getNewItems("http://cloud.csdn.net/cloud", 1);
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
    public void loadMoreData()
    {
        // 当前数据是从网络获取的
        currentPage += 1;
        try
        {
            List<NewItem> newsItems = mNewItemBiz.getNewItems("http://cloud.csdn.net/cloud", currentPage);
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
    class LoadDatasTask extends AsyncTask<Integer, Void, Integer>
    {

        @Override
        protected Integer doInBackground(Integer... params){
            switch (params[0]){
                case LOAD_MORE:
                    loadMoreData();
                    break;
                case LOAD_REFREASH:
                    return refreashData();
            }
            return -1;
        }

        @Override
        protected void onPostExecute(Integer result){
            switch (result){
//                case TIP_ERROR_NO_NETWORK:
//                    ToastUtil.toast(getActivity(), "没有网络连接！");
//                    mAdapter.setDatas(mDatas);
//                    mAdapter.notifyDataSetChanged();
//                    break;
//                case TIP_ERROR_SERVER:
//                    ToastUtil.toast(getActivity(), "服务器错误！");
//                    break;
                default:
                    break;

            }
            mAdapter.notifyDataSetChanged();
            content_items.onRefreshComplete();
        }

    }
}
