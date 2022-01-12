package com.swu.module_order.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.base.BaseFragment;
import com.swu.module_order.adapter.ConfirmOrderGoodsAdapter;
import com.swu.module_order.databinding.FragmentConfirmOrderBinding;

import java.util.ArrayList;

public class ConfirmOrderFragment extends BaseFragment<FragmentConfirmOrderBinding> {

    @Override
    public void initViews() {
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

    @Override
    public void initListener() {

    }
}