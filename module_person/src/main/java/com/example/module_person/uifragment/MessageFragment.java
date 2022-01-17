package com.example.module_person.uifragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseFragment;
import com.common.constants.RouteConstants;
import com.common.constants.TargetFragmentConstants;
import com.common.selfview.MyTitleBar;
import com.example.module_person.adapter.MessageAdapter;
import com.swu.module_person.R;
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
        MyTitleBar myTitleBar = getActivity().findViewById(R.id.titleBar);
        //点击返回时，跳转到UserFragment
        myTitleBar.setOnMyTitleBarListener(new MyTitleBar.OnMyTitleBarListener() {
            @Override
            public void onLeftClick() {
                ARouter.getInstance().build(RouteConstants.Module_app.PAGER_NAVIGATION)
                        .withString("targetFragment", TargetFragmentConstants.USER_FRAGMENT).navigation();

            }

            @Override
            public void onRightClick(View v) {

            }
        });
        ArrayList<String> mDatas = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            mDatas.add("第 " + i + " 个item");
        }
        getBinding().messageMessageRecyclerview.setAdapter(new MessageAdapter(getActivity(),getContext(),mDatas));
        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getBinding().messageMessageRecyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();//对返回键做特殊处理
    }

    //主界面获取焦点
    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // 监听到返回按钮点击事件
                    ARouter.getInstance().build(RouteConstants.Module_app.PAGER_NAVIGATION)
                            .withString("targetFragment", TargetFragmentConstants.USER_FRAGMENT).navigation();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void initListener() {

    }


}