package com.yzq.mvvm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.yzq.core.base.BaseActivity;
import com.yzq.mvvm.adapter.PhotoLoadMoreAdapter;
import com.yzq.mvvm.bean.GankIoDataBean;
import com.yzq.mvvm.databinding.ActivityMainBinding;
import com.yzq.mvvm.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {
    private BaseQuickAdapter adapter;
    private List list=new ArrayList();
    private int PAGE=1;
    private int PAGE_SIZE=10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBackTitle("美女图片");
        adapter=new PhotoLoadMoreAdapter(R.layout.item_girl,list);
        adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                viewModel.getData(PAGE);
            }
        });
        bindingView.rv.setLayoutManager(new GridLayoutManager(this,2));
        bindingView.rv.setAdapter(adapter);
        viewModel.getGankIoDataBeanMutableLiveData().observe(this, new Observer<GankIoDataBean>() {
            @Override
            public void onChanged(GankIoDataBean cityListsBean) {
                showContentView();
                if (cityListsBean!=null){
                    if (PAGE==0){
                        adapter.setList(cityListsBean.getResults());
                    }else {
                        adapter.addData(cityListsBean.getResults());
                    }
                    if (cityListsBean.getResults().size()<PAGE_SIZE){
                        adapter.getLoadMoreModule().loadMoreEnd();
                    }else {
                        adapter.getLoadMoreModule().loadMoreComplete();
                    }
                    PAGE++;
                }else {
                    showError();
                }

            }
        });
    }

    @Override
    protected void start() {
        super.start();
        viewModel.getData(PAGE);
    }
}
