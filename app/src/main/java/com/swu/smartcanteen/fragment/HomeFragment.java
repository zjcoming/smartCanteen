package com.swu.smartcanteen.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.ApplicationContext;
import com.base.BaseFragment;
import com.base.util.UIUtils;
import com.common.constants.DiningWay;
import com.common.constants.PermissionConstants;
import com.common.constants.RouteConstants;
import com.common.util.DialogUtil;
import com.common.util.LogUtil;
import com.common.util.PermissionUtil;
import com.swu.smartcanteen.R;
import com.swu.smartcanteen.databinding.FragmentHomeBinding;

import java.util.Arrays;

/**
 * created by zhangjun
 * data 2021/11/27
 * des:
 */

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    @Override
    public void onResume() {
        super.onResume();

        PermissionUtil.requestMustPermissions(getContext());//申请必要权限
    }

    @Override
    public void initListener() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onClick(View view) {
                String diningStyle = "";
                switch (view.getId()) {
                    case R.id.take_out:
                        diningStyle = DiningWay.TAKE_OUT;
                        break;
                    case R.id.pre_order:
                        diningStyle = DiningWay.PRE_ORDER;
                        break;
                    case R.id.in_canteen:
                        diningStyle = DiningWay.IN_CANTEEN;
                        break;
                    default:
                        LogUtil.e("click area error, not find correct id");
                }
                ARouter.getInstance().build(RouteConstants.Module_order.PAGER_CHOOSE_FOOD)
                        .withString(DiningWay.DINING_WAY, diningStyle)
                        .navigation();
            }
        };

        getBinding().inCanteen.setOnClickListener(clickListener);
        getBinding().preOrder.setOnClickListener(clickListener);
        getBinding().takeOut.setOnClickListener(clickListener);

    }

    @Override
    public void initViews() {

    }
}