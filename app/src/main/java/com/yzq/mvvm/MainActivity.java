package com.yzq.mvvm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yzq.core.base.BaseActivity;
import com.yzq.core.data.callback.StateData;
import com.yzq.core.data.callback.Status;
import com.yzq.core.utils.LogUtil;
import com.yzq.core.widget.CoreRecyclerView;
import com.yzq.core.widget.TitleBuilder;
import com.yzq.mvvm.adapter.PhotoLoadMoreAdapter;
import com.yzq.mvvm.bean.GankIoDataBean;
import com.yzq.mvvm.databinding.ActivityMainBinding;
import com.yzq.mvvm.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {
    private BaseQuickAdapter adapter;
    private List list=new ArrayList();
    private int page=1;
    private int pageSize=50;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TitleBuilder builder= initBackTitle("美女图片");
        builder.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, com.yzq.testlibrary.MainActivity.class));
            }
        });
    }

    @Override
    protected void start() {
        super.start();
        adapter=new PhotoLoadMoreAdapter(R.layout.item_girl,list);
        bindingView.vg2.setAdapter(adapter);
        bindingView.vg2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewModel.getGankIoDataBeanMutableLiveData().observe(this, new Observer<StateData<GankIoDataBean>>() {
            @Override
            public void onChanged(StateData<GankIoDataBean> cityListsBean) {
                showContentView();
                if (cityListsBean.getStatus()== Status.OK){
                    if (page==1){
                        adapter.setList(cityListsBean.getData().getResults());
                    }else {
                        adapter.addData(cityListsBean.getData().getResults());
                    }
                }else {
                    if (page==1){
                        showError();
                    }else {
                        adapter.getLoadMoreModule().loadMoreFail();
                    }
                }
            }
        });
        viewModel.getData(1);
    }

    @Override
    protected void tryToLoad() {
        super.tryToLoad();
       viewModel.getData(1);
    }

}
