package com.example.module_person.dataFragment;

import androidx.lifecycle.ViewModelProvider;

import android.view.View;

import com.base.BaseFragment;
import com.base.util.FragmentUtil;
import com.common.selfview.MyTitleBar;
import com.example.module_person.viewmodel.PersonDataViewModel;
import com.swu.module_person.R;
import com.swu.module_person.databinding.FragmentAddAddressBinding;


public class AddAddressFragment extends BaseFragment<FragmentAddAddressBinding> {

    private PersonDataViewModel personDataViewModel;
    @Override
    public void initViews() {

        personDataViewModel = new ViewModelProvider(requireActivity()).get(PersonDataViewModel.class);
    }

    @Override
    public void initListener() {

        onBackClick();
    }
    private void onBackClick() {
        getBinding().btBack.setOnMyTitleBarListener(new MyTitleBar.OnMyTitleBarListener() {
            @Override
            public void onLeftClick() {
//                FragmentUtil.getInstance().switchFragment(requireActivity(),personDataViewModel.addAddressFragment,personDataViewModel.addressListFragment, R.id.content_container, com.swu.lib_common.R.anim.page_from_left_to_right_out);

            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }
}