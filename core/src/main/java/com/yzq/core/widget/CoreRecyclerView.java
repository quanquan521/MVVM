package com.yzq.core.widget;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yzq.core.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by hpw on 16/11/1.
 */

public class CoreRecyclerView extends LinearLayout implements OnRefreshListener, OnLoadMoreListener {
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSwipeRefreshLayout;
    BaseQuickAdapter mQuickAdapter;
    addDataListener addDataListener;
    public interface addDataListener {
        void addData(int page);
    }
    private int page=1;
    private  int pageSize=20;  //可以根据总页数判断分页
    public CoreRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public CoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CoreRecyclerView initView(Context context) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_recyclerview, null);
        view.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        addView(view);
        mSwipeRefreshLayout = (SmartRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setEnableRefresh(false);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        return this;
    }

    public CoreRecyclerView init(BaseQuickAdapter mQuickAdapter) {
        init(null, mQuickAdapter);
        return this;
    }

    public CoreRecyclerView init(BaseQuickAdapter mQuickAdapter, Boolean isRefresh) {
        init(null, mQuickAdapter, isRefresh);
        return this;
    }

    public CoreRecyclerView init(RecyclerView.LayoutManager layoutManager, BaseQuickAdapter mQuickAdapter) {
        init(layoutManager, mQuickAdapter, false);
        return this;
    }

    public CoreRecyclerView init(RecyclerView.LayoutManager layoutManager, BaseQuickAdapter mQuickAdapter, Boolean isRefresh) {
        if (isRefresh != true) {
            mSwipeRefreshLayout.setEnableRefresh(false);
        }else {
            mSwipeRefreshLayout.setEnableRefresh(true);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
        mRecyclerView.setLayoutManager(layoutManager != null ? layoutManager : new LinearLayoutManager(getContext()));
        this.mQuickAdapter = mQuickAdapter;
        mQuickAdapter.setAnimationEnable(true);
        mRecyclerView.setAdapter(mQuickAdapter);
        return this;
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
       load(1);
       overRefreshing();
    }

    @Override
    public void onLoadMore() {
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (mQuickAdapter.getData().size() < page * pageSize) {
                    mQuickAdapter.getLoadMoreModule().loadMoreEnd();
                } else {
                     page += 1;
                     addDataListener.addData(page);
                }
            }
        });
    }

    public CoreRecyclerView overRefreshing() {
        mSwipeRefreshLayout.finishRefresh();
        return this;
    }

    public CoreRecyclerView openLoadMore(addDataListener addDataListener) {
        this.addDataListener = addDataListener;
        mQuickAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
        return this;
    }
    public CoreRecyclerView loadMoreFailed() {
        page--;
        mQuickAdapter.getLoadMoreModule().loadMoreFail();
        return this;
    }
    public CoreRecyclerView loadMoreComplete() {
        mQuickAdapter.getLoadMoreModule().loadMoreComplete();
        return this;
    }
    public void  load(int page){
        this.page = page;
        if (addDataListener!=null)
        addDataListener.addData(page);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

