package com.example.module_person.uifragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseFragment;
import com.common.constants.RouteConstants;
import com.common.constants.TargetFragmentConstants;
import com.common.selfview.MyTitleBar;
import com.common.util.DialogUtil;
import com.example.module_person.adapter.MessageAdapter;
import com.example.module_person.viewmodel.MessageViewModel;
import com.swu.module_person.R;
import com.swu.module_person.databinding.FragmentMessageBinding;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 刘金豪 on 2021/1/15
 * desc: “我的消息”页面
 */
@Route(path = RouteConstants.Module_person.PAGER_MESSAGE_FRAGMENT)
public class MessageFragment extends BaseFragment<FragmentMessageBinding> {
    MessageViewModel messageViewModel;

    /**
     * 初始化viewModel
     */
    public void initViewModel(){
        //得到viewModel
        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        //监听viewmodel里面的数据变化
        messageViewModel.getAllMessages().observe(this, new Observer<ArrayList<HashMap<String, String>>>() {
            @Override
            public void onChanged(ArrayList<HashMap<String, String>> hashMaps) {
                //当 消息 数据发生变化时，改变RecyclerView中显示的数据
                getBinding().messageMessageRecyclerview.setAdapter(new MessageAdapter(getActivity(),getContext(),hashMaps));
            }
        });
        messageViewModel.getLoadingLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //监听是否显示“加载中”dialog
                Dialog loadingDialog = DialogUtil.createLoadingDialog(getContext(), "加载中", true, true);
                if (aBoolean){
                    loadingDialog.show();
                }else {
                    loadingDialog.dismiss();
                }
            }
        });

        //添加观察者，把messageViewModel作为生命周期观察者
        getLifecycle().addObserver(messageViewModel);
    }


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
            public void onRightClick(View v) { }
        });

        //RecyclerView的线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getBinding().messageMessageRecyclerview.setLayoutManager(linearLayoutManager);

        //控件初始化完成后，初始化ViewModel
        initViewModel();
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
                            .withString("targetFragment", TargetFragmentConstants.USER_FRAGMENT)
                            .navigation();
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