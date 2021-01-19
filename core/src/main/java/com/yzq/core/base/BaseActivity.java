package com.yzq.core.base;

import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.yzq.core.R;
import com.yzq.core.RxManager;
import com.yzq.core.databinding.ActivityBaseBinding;
import com.yzq.core.utils.ClassUtil;
import com.yzq.core.widget.LoadingLayout;
import com.yzq.core.widget.TitleBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * 版权： 版权所有
 * <p>
 * 作者：无敌小圈圈
 * <p>
 * 版本：1.0
 * <p>
 * 创建日期：on 2020/9/15.
 * <p>
 * 描述：
 */
public abstract class BaseActivity<VM extends AndroidViewModel,SV extends ViewDataBinding> extends AppCompatActivity {
    // ViewModel
    protected VM viewModel;
    // 布局view
    protected SV bindingView;
    private ActivityBaseBinding mBaseBinding;
    private LoadingLayout loadingLayout;

    @Override
    public void setContentView(int layoutResID) {
        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);
        bindingView = DataBindingUtil.inflate(LayoutInflater.from(this), layoutResID, null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = (RelativeLayout) mBaseBinding.getRoot().findViewById(R.id.container);
        mContainer.addView(bindingView.getRoot());
        loadingLayout= (LoadingLayout) LayoutInflater.from(this).inflate(R.layout.loading_layout,null);
        loadingLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mContainer.addView(loadingLayout);
        getWindow().setContentView(mBaseBinding.getRoot());
        showLoading();
        bindingView.getRoot().setVisibility(View.GONE);
        initStatusBar();
        initViewModel();
        start();
    }
    public void initStatusBar() {
        ImmersionBar.with(this).statusBarColor(R.color.colorWhite) .statusBarDarkFont(true, 0.2f) .navigationBarColor(R.color.colorWhite).fitsSystemWindows(true).init();;
    }

    protected TitleBuilder initBackTitle(String title) {
        return new TitleBuilder(this)
                .setTitleText(title)
                .setLeftImage(R.mipmap.ic_launcher)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }
    private void initViewModel() {
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            this.viewModel = new ViewModelProvider(this).get(viewModelClass);
        }
    }
    protected void showContentView() {
        loadingLayout.showContentView();
        if (bindingView.getRoot().getVisibility() != View.VISIBLE) {
            bindingView.getRoot().setVisibility(View.VISIBLE);
        }
    }
    protected void showLoading() {
        loadingLayout.showLoading();
        /*if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }*/
    }
    protected void showError() {
        loadingLayout.showError();
        loadingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                tryToLoad();
            }
        });
        if (bindingView.getRoot().getVisibility() != View.GONE) {
            bindingView.getRoot().setVisibility(View.GONE);
        }
    }

    /**
     * 失败后点击刷新
     */
    protected void tryToLoad() {}
    /*
    * 加载数据
    *
    * */
    protected void start() {

    }

}
