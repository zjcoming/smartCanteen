package com.swu.module_order.fragment;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.BaseFragment;
import com.common.util.FragmentUtil;
import com.common.util.LogUtil;
import com.swu.module_order.databinding.FragmentBuyCarBottomBinding;

/**
 * @ClassName BuyCarBottomFragment
 * @Author zhangJun
 * @Date 2022/1/12 6:20 下午
 * @Description
 */
public class BuyCarBottomFragment extends BaseFragment<FragmentBuyCarBottomBinding> {
    private LinearLayout selectList;
    private TextView goPay;
    private FragmentUtil fragmentUtil;
    @Override
    protected void initViews() {
        selectList = getBinding().checkSelectList;
        goPay = getBinding().goPay;
        fragmentUtil = new FragmentUtil();

    }

    @Override
    protected void initListener() {
        selectList.setOnClickListener(view -> {
            LogUtil.e("购物车");
            Toast.makeText(getContext(), "购物车", Toast.LENGTH_SHORT).show();
        });
        goPay.setOnClickListener(view -> {
            LogUtil.e("去结算");
            Toast.makeText(getContext(), "去结算", Toast.LENGTH_SHORT).show();
        });
    }
}
