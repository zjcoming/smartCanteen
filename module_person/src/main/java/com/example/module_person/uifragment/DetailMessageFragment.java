package com.example.module_person.uifragment;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseFragment;
import com.common.constants.RouteConstants;
import com.common.constants.TargetFragmentConstants;
import com.common.selfview.MyTitleBar;
import com.swu.module_person.databinding.FragmentDetailMessageBinding;


/**
 * Created by 刘金豪 on 2021/1/15
 * desc: 具体消息页面
 */
@Route(path = RouteConstants.Module_person.PAGER_DETAIL_MESSAGE_FRAGMENT)
public class DetailMessageFragment extends BaseFragment<FragmentDetailMessageBinding> {
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
        getBinding().detailMessageTitle.setText("谢靖是傻逼");
        getBinding().detailMessageContent.setText("这是消息的内容");
        getBinding().detailMessageTime.setText("15:40");
    }

    @Override
    public void initListener() {

    }
}