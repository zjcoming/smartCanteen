package com.example.module_person;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseActivity;
import com.base.model.MessageModel;
import com.common.constants.TargetFragmentConstants;
import com.common.constants.RouteConstants;
import com.common.requestbase.AppObserver;
import com.common.util.FragmentUtil;
import com.example.module_person.uifragment.DetailMessageFragment;
import com.example.module_person.uifragment.MessageFragment;
import com.swu.module_person.R;
import com.swu.module_person.databinding.ActivityUIBinding;

import java.io.Serializable;
import java.util.HashMap;

import io.reactivex.annotations.NonNull;

/**
 * Created by 刘金豪 on 2021/1/10
 * desc: 控制person模块的界面展示
 */
@Route(path = RouteConstants.Module_person.PAGER_UI_ACTIVITY)
public class UIActivity extends BaseActivity<ActivityUIBinding> {
    @Autowired
    String targetFragment;

    MessageFragment messageFragment;

    /**
     * 与detailMessageFragment相关的集合
     */
    DetailMessageFragment detailMessageFragment;
    @Autowired
    MessageModel msgDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_i);
        //依赖注入
        ARouter.getInstance().inject(this);

        messageFragment = new MessageFragment();
        detailMessageFragment = new DetailMessageFragment();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (targetFragment == null){
            return;
        }
        switch (targetFragment){
            case TargetFragmentConstants.MESSAGE_FRAGMENT:
                //显示消息Fragment
                FragmentUtil.getInstance().startFragment(this,messageFragment,R.id.ui_content_container);
                break;
            case TargetFragmentConstants.DETAIL_MESSAGE_FRAGMENT:
                //传递参数
                Bundle args = new Bundle();
                args.putSerializable("msgDetail", msgDetail);
                detailMessageFragment.setArguments(args);
                //显示消息Fragment
                FragmentUtil.getInstance().startFragment(this,detailMessageFragment,R.id.ui_content_container);
                break;
            default:
                //默认跳转到消息Fragment
                //FragmentUtil.getInstance().startFragment(this,new MessageFragment(),R.id.ui_content_container);
                break;
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}