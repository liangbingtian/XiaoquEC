package com.example.latte.ui.refresh;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.latte.app.Latte;
import com.example.latte.net.RestClient;
import com.example.latte.net.callback.ISuccess;
import com.example.latte.ui.recycler.DataConverter;
import com.example.latte.ui.recycler.MultipleRecyclerAdapter;

/**
 * Created by liangbingtian on 2018/4/8.
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener
,BaseQuickAdapter.RequestLoadMoreListener{

    private final SwipeRefreshLayout REFRESH_LAYOUT;

    private static final Handler HANDLER = new Handler();

    private final PagingBean BEAN;
    @SuppressWarnings("SpellCheckingInspection")
    private final RecyclerView RECYCLEVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConverter CONVERTER;

    private RefreshHandler(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView, DataConverter dataConverter, PagingBean bean) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLEVIEW = recyclerView;
        this.CONVERTER = dataConverter;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView, DataConverter dataConverter){
        return new RefreshHandler(swipeRefreshLayout, recyclerView, dataConverter, new PagingBean());
    }

    private void refresh() {
        REFRESH_LAYOUT.setRefreshing(true);
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                //可以进行一些网络请求，当网络请求结束之后把下边的方法放在网络请求成功的回调里面
                REFRESH_LAYOUT.setRefreshing(false);
            }
        }, 2000);
    }

    public void firstPage(String url) {
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                        //设置Adapter
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this,RECYCLEVIEW);
                        RECYCLEVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
