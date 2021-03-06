package com.example.module_person.dataFragment;

import androidx.lifecycle.ViewModelProvider;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseFragment;
import com.common.constants.RouteConstants;
import com.common.constants.TargetFragmentConstants;
import com.common.selfview.MyTitleBar;
import com.base.FragmentUtil;
import com.example.module_person.viewmodel.PersonDataViewModel;
import com.swu.module_person.R;
import com.swu.module_person.databinding.FragmentPersonDataBinding;

public class PersonDataFragment extends BaseFragment<FragmentPersonDataBinding> {

    private PersonDataViewModel personDataViewModel;
    @Override
    public void initViews() {
        //ARouter依赖注入
        ARouter.getInstance().inject(this);
    }

    @Override
    public void initListener() {
        personDataViewModel = new ViewModelProvider(requireActivity()).get(PersonDataViewModel.class);
        getBinding().btnAddressSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentUtil.getInstance().switchFragment(requireActivity(), personDataViewModel.personDataFragment, personDataViewModel.addressListFragment,R.id.container, com.swu.lib_common.R.anim.page_from_right_to_left_in);
            }
        });
        onBackClick();
    }
    private void onBackClick() {
        getBinding().btBack.setOnMyTitleBarListener(new MyTitleBar.OnMyTitleBarListener() {
            @Override
            public void onLeftClick() {
                ARouter.getInstance().build(RouteConstants.Module_app.PAGER_NAVIGATION)
                        .withString("targetFragment", TargetFragmentConstants.USER_FRAGMENT).navigation();
            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }
}