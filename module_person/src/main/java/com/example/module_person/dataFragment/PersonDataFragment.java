package com.example.module_person.dataFragment;

import androidx.lifecycle.ViewModelProvider;

import android.view.View;

import com.base.BaseFragment;
import com.common.util.FragmentUtil;
import com.example.module_person.viewmodel.PersonDataViewModel;
import com.swu.module_person.R;
import com.swu.module_person.databinding.FragmentPersonDataBinding;

public class PersonDataFragment extends BaseFragment<FragmentPersonDataBinding> {

    private PersonDataViewModel personDataViewModel;
    @Override
    public void initViews() {

    }

    @Override
    public void initListener() {
        personDataViewModel = new ViewModelProvider(requireActivity()).get(PersonDataViewModel.class);
        getBinding().btnAddressSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentUtil.getInstance().switchFragment(requireActivity(), personDataViewModel.personDataFragment, personDataViewModel.addressListFragment, R.id.content_container, com.swu.lib_common.R.anim.page_from_right_to_left_in);
                FragmentUtil.getInstance().switchFragment(requireActivity(), personDataViewModel.dataTop, personDataViewModel.setTop, R.id.top_container, com.swu.lib_common.R.anim.page_from_right_to_left_in);
            }
        });
    }
}