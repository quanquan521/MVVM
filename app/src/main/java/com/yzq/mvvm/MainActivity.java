package com.yzq.mvvm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.yzq.core.base.BaseActivity;
import com.yzq.core.utils.LogUtil;
import com.yzq.core.widget.CoreRecyclerView;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBackTitle("美女图片");
    }

    @Override
    protected void start() {
        super.start();
        adapter=new PhotoLoadMoreAdapter(R.layout.item_girl,list);
        bindingView.rv.init(new GridLayoutManager(this,2),adapter,true);
        bindingView.rv.openLoadMore(new CoreRecyclerView.addDataListener() {
            @Override
            public void addData(int page) {
                viewModel.getData(page);
            }
        });
        viewModel.getGankIoDataBeanMutableLiveData().observe(this, new Observer<GankIoDataBean>() {
            @Override
            public void onChanged(GankIoDataBean cityListsBean) {
                showContentView();
                if (cityListsBean!=null){
                    if (bindingView.rv.getPage()==1){
                        adapter.setList(cityListsBean.getResults());
                    }else {
                        adapter.addData(cityListsBean.getResults());
                    }
                     bindingView.rv.loadMoreComplete();
                }else {
                    if (bindingView.rv.getPage()==1){
                        showError();
                    }else {
                        bindingView.rv.loadMoreFailed();
                    }
                }
            }
        });
        bindingView.rv.load(1);
    }

    @Override
    protected void tryToLoad() {
        super.tryToLoad();
        bindingView.rv.load(1);
    }
}
