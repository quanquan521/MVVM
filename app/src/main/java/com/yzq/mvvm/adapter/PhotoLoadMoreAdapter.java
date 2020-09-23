package com.yzq.mvvm.adapter;

import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.jakewharton.rxbinding.view.RxView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import androidx.databinding.DataBindingUtil;
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
public  class PhotoLoadMoreAdapter extends BaseQuickAdapter<GankIoDataBean.ResultBean, BaseDataBindingHolder> implements LoadMoreModule {


    public PhotoLoadMoreAdapter(int layoutResId, @Nullable List<GankIoDataBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder baseViewHolder, GankIoDataBean.ResultBean o) {
        final ItemGirlBinding mBindHeader = DataBindingUtil.getBinding(baseViewHolder.itemView);
        mBindHeader.setItem(o);
        mBindHeader.executePendingBindings();
        RxView.clicks(mBindHeader.ivWelfare).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Toast.makeText(getContext(),"aaaaaaaa",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
