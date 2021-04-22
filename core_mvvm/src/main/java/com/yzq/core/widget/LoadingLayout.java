package com.yzq.core.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yzq.core.R;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 版权： 版权所有
 * <p>
 * 作者：无敌小圈圈
 * <p>
 * 版本：1.0
 * <p>
 * 创建日期：on 2020/11/11.
 * <p>
 * 描述：
 */
 public class LoadingLayout extends FrameLayout {
    private View loadingView;
    private View errorView;
    private AnimationDrawable mAnimationDrawable;
    private boolean isLoading;

    public LoadingLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    void init(Context context){
        loadingView= LayoutInflater.from(context).inflate(R.layout.layout_loading_view,null);
        errorView=LayoutInflater.from(context).inflate(R.layout.layout_loading_error,null);
        addView(loadingView);addView(errorView);
        ImageView img = loadingView.findViewById(R.id.img_progress);
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
    }

    public void showContentView(){
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        loadingView.setVisibility(GONE);
        errorView.setVisibility(GONE);
        setClickable(false);
        isLoading=false;
    }
    public void showLoading() {
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        loadingView.setVisibility(VISIBLE);
        errorView.setVisibility(GONE);
        setClickable(true);
        isLoading=true;
    }
    public void showError() {
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        loadingView.setVisibility(GONE);
        errorView.setVisibility(VISIBLE);
        isLoading=false;
    }
    public boolean isLoading(){
        return isLoading;
    }
}
