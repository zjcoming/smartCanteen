package com.swu.module_order.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.BaseFragment;
import com.swu.module_order.adapter.ConfirmOrderGoodsAdapter;
import com.swu.module_order.databinding.FragmentConfirmOrderBinding;

import java.util.ArrayList;

public class ConfirmOrderFragment extends BaseFragment<FragmentConfirmOrderBinding> {

    @Override
    protected void initViews() {
        super.initViews();
        //初始化商品列表
        initGoods();
    }
    public void initGoods(){
        ArrayList<String> mDatas = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            mDatas.add("第 " + i + " 个item");
        }

        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getBinding().confirmOrderRecyclerview.setLayoutManager(linearLayoutManager);

        ConfirmOrderGoodsAdapter adapter = new ConfirmOrderGoodsAdapter(getContext(), mDatas);
        getBinding().confirmOrderRecyclerview.setAdapter(adapter);
    }
}