package com.example.module_person.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseFragment;
import com.common.constants.RouteConstants;
import com.common.selfview.MyTitleBar;
import com.common.util.FragmentUtil;
import com.example.module_person.adapter.MessageAdapter;
import com.swu.module_person.databinding.FragmentMessageBinding;


import java.util.ArrayList;

/**
 * Created by 刘金豪 on 2021/1/15
 * desc: “我的消息”页面
 */
@Route(path = RouteConstants.Module_person.PAGER_MESSAGE_FRAGMENT)
public class MessageFragment extends BaseFragment<FragmentMessageBinding> {


    @Override
    public void initViews() {
        MyTitleBar myTitleBar = getBinding().messageTitlebar;
        //点击返回时，跳转到UserFragment
        myTitleBar.setOnMyLeftTitleBarListener(() -> ARouter.getInstance().build(RouteConstants.Module_app.PAGER_USER_FRAGMENT).navigation());
        ArrayList<String> mDatas = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            mDatas.add("第 " + i + " 个item");
        }
        getBinding().messageMessageRecyclerview.setAdapter(new MessageAdapter(getActivity(),getContext(),mDatas));
        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getBinding().messageMessageRecyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initListener() {

    }
}