package com.example.module_person.uifragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseFragment;
import com.common.constants.RouteConstants;
import com.common.constants.TargetFragmentConstants;
import com.common.model.MessageModel;
import com.common.selfview.MyTitleBar;
import com.swu.module_person.databinding.FragmentDetailMessageBinding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Created by 刘金豪 on 2021/1/15
 * desc: 具体消息页面
 */
@Route(path = RouteConstants.Module_person.PAGER_DETAIL_MESSAGE_FRAGMENT)
public class DetailMessageFragment extends BaseFragment<FragmentDetailMessageBinding> {
    String time;
    String title;
    String content;
    MessageModel msgDetail;

    @Override
    public void initViews() {
        getBinding().titleBar.setOnMyTitleBarListener(new MyTitleBar.OnMyTitleBarListener() {
            @Override
            public void onLeftClick() {
                ARouter.getInstance().build(RouteConstants.Module_person.PAGER_UI_ACTIVITY)
                        .withString("targetFragment", TargetFragmentConstants.MESSAGE_FRAGMENT)
                        .navigation();
            }
            @Override
            public void onRightClick(View v) {
            }
        });

        getBinding().detailMessageTime.setText(time);
        getBinding().detailMessageTitle.setText(title);
        getBinding().detailMessageContent.setText("  " + content);
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //取出参数，用于显示message
        if (getArguments() != null){
            msgDetail = (MessageModel)getArguments().getSerializable("msgDetail");
            time = msgDetail.getTime();
            title = msgDetail.getTitle();
            content = msgDetail.getContent();
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();//对返回键做特殊处理
    }

    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // 监听到返回按钮点击事件
                    ARouter.getInstance().build(RouteConstants.Module_person.PAGER_UI_ACTIVITY)
                            .withString("targetFragment", TargetFragmentConstants.MESSAGE_FRAGMENT)
                            .navigation();
                    return true;
                }
                return false;
            }
        });
    }
}