package com.swu.smartcanteen.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.annotation.NonNull;

import com.base.ApplicationContext;
import com.base.BaseFragment;
import com.base.util.UIUtils;
import com.common.constants.PermissionConstants;
import com.common.util.DialogUtil;
import com.common.util.PermissionUtil;
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
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}