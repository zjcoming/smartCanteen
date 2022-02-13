package com.example.module_person;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseActivity;
import com.common.constants.TargetFragmentConstants;
import com.common.constants.RouteConstants;
import com.common.model.MessageModel;
import com.example.module_person.dataFragment.PersonDataFragment;
import com.example.module_person.uifragment.DetailMessageFragment;
import com.example.module_person.uifragment.MessageFragment;
import com.example.module_person.viewmodel.PersonDataViewModel;
import com.swu.module_person.R;
import com.swu.module_person.databinding.ActivityUIBinding;

/**
 * Created by 刘金豪 on 2021/1/10
 * desc: 控制person模块的界面展示
 */
@Route(path = RouteConstants.Module_person.PAGER_UI_ACTIVITY)
public class UIActivity extends BaseActivity<ActivityUIBinding> {
    @Autowired
    String targetFragment;

    MessageFragment messageFragment;

    PersonDataViewModel personDataViewModel;
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

        personDataViewModel = new ViewModelProvider(this).get(PersonDataViewModel.class);
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
                openFragment(messageFragment);
//                FragmentUtil.getInstance().startFragment(this,messageFragment,R.id.ui_content_container);
                break;
            case TargetFragmentConstants.DETAIL_MESSAGE_FRAGMENT:
                //传递参数
                Bundle args = new Bundle();
                args.putSerializable("msgDetail", msgDetail);
                detailMessageFragment.setArguments(args);
                //显示消息Fragment
                openFragment(detailMessageFragment);
//                FragmentUtil.getInstance().startFragment(this,detailMessageFragment,R.id.ui_content_container);
                break;
            case TargetFragmentConstants.DATA_PERSON:
                openFragment(personDataViewModel.personDataFragment);
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