package com.yzq.mvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jakewharton.rxbinding.view.RxView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import androidx.databinding.DataBindingUtil;

import com.yzq.mvvm.R;
import com.yzq.mvvm.bean.GankIoDataBean;
import com.yzq.mvvm.databinding.ItemGirlBinding;

import rx.functions.Action1;

/**
 * 版权： 版权所有
 * <p>
 * 作者：无敌小圈圈
 * <p>
 * 版本：1.0
 * <p>
 * 创建日期：on 2020/9/18.
 * <p>
 * 描述：
 */
public  class PhotoLoadMoreAdapter extends BaseQuickAdapter<GankIoDataBean.ResultBean, BaseViewHolder>{


    public PhotoLoadMoreAdapter(int layoutResId, @Nullable List<GankIoDataBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, GankIoDataBean.ResultBean o) {
        ImageView iv_welfare=baseViewHolder.getView(R.id.iv_welfare);
        Glide.with(getContext()).load(o.getUrl()).into(iv_welfare);
        RxView.clicks(iv_welfare).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Toast.makeText(getContext(),"aaaaaaaa",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
