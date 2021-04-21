package com.yzq.core.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.yzq.core.R;
import com.yzq.core.databinding.ActivityBaseBinding;
import com.yzq.core.utils.ClassUtil;
import com.yzq.core.widget.LoadingLayout;
import com.yzq.core.widget.TitleBuilder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 版权： 版权所有
 * <p>
 * 作者：无敌小圈圈
 * <p>
 * 版本：1.0
 * <p>
 * 创建日期：on 2021/4/7.
 * <p>
 * 描述：
 */
public abstract class BaseFragment<VM extends AndroidViewModel,SV extends ViewDataBinding> extends SupportFragment {
    // ViewModel
    protected VM viewModel;
    // 布局view
    protected SV bindingView;
    private ActivityBaseBinding mBaseBinding;
    private LoadingLayout loadingLayout;
    protected Context mContext;
    protected Activity mActivity;
    public interface OnBackToFirstListener {
        void onBackToFirstFragment();
    }
    protected OnBackToFirstListener _mBackToFirstListener;
    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
        if (context instanceof OnBackToFirstListener) {
            _mBackToFirstListener = (OnBackToFirstListener) context;
        }
    }
    public abstract int getLayoutId();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBaseBinding = DataBindingUtil.inflate(inflater, R.layout.activity_base, null, false);
        bindingView = DataBindingUtil.inflate(inflater, getLayoutId(), null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        RelativeLayout mContainer = (RelativeLayout) mBaseBinding.getRoot().findViewById(R.id.container);
        mContainer.addView(bindingView.getRoot());
        loadingLayout= (LoadingLayout)inflater.inflate(R.layout.loading_layout,null);
        loadingLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mContainer.addView(loadingLayout);
        showLoading();
        bindingView.getRoot().setVisibility(View.GONE);
        initStatusBar();
        initBackTitle(mBaseBinding.getRoot());
        initViewModel();
        start();
        return mBaseBinding.getRoot();
    }
    /**
     * 处理回退事件
     * 如果是孩子fragment需要重写onBackPressedSupport(){_mBackToFirstListener.onBackToFirstFragment();return true;}
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        Log.i("dfasdfsdf",getChildFragmentManager().getBackStackEntryCount()+"");
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if (_mBackToFirstListener!=null){
                _mBackToFirstListener.onBackToFirstFragment();
            }else {
                _mActivity.finish();
            }
        }
        return true;
    }
    private void initBackTitle(View v) {
        TitleBuilder titleBuilder=new TitleBuilder(v);
        titleBuilder.getRootView().setVisibility(View.GONE);
    }
    public void initStatusBar() {

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
