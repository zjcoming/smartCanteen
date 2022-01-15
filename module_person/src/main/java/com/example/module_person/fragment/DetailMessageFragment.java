package com.example.module_person.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseFragment;
import com.common.constants.RouteConstants;
import com.common.util.FragmentUtil;
import com.swu.module_person.databinding.FragmentDetailMessageBinding;


/**
 * Created by 刘金豪 on 2021/1/15
 * desc: 具体消息页面
 */
@Route(path = RouteConstants.Module_person.PAGER_DETAIL_MESSAGE_FRAGMENT)
public class DetailMessageFragment extends BaseFragment<FragmentDetailMessageBinding> {
    @Override
    public void initViews() {
        getBinding().detailMessageTitlebar.setOnMyLeftTitleBarListener(() -> ARouter.getInstance().build(RouteConstants.Module_person.PAGER_MESSAGE_FRAGMENT).navigation());
        getBinding().detailMessageTitle.setText("谢靖是傻逼");
        getBinding().detailMessageContent.setText("这是消息的内容");
        getBinding().detailMessageTime.setText("15:40");
    }

    @Override
    public void initListener() {

    }
}