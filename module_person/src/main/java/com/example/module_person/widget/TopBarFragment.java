package com.example.module_person.widget;


import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.base.BaseFragment;
import com.example.module_person.viewmodel.PersonDataViewModel;
import com.swu.module_person.databinding.FragmentTopBarBinding;

public class TopBarFragment extends BaseFragment<FragmentTopBarBinding> {
    private  String type;
    private TopBarFragment(String type){
        this.type = type;
    }
    public static TopBarFragment getInstance(String type){
        return new TopBarFragment(type);
    }
    private PersonDataViewModel personDataViewModel;
    @Override
    public void initViews() {
        getBinding().tvMessage.setText(type);

        personDataViewModel = new ViewModelProvider(requireActivity()).get(PersonDataViewModel.class);
    }

    @Override
    public void initListener() {
        getBinding().ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                switch (type){
//                    case TopFragmentType.PERSON_DATA:
//                        //
//                        break;
//                    case TopFragmentType.SET_ADDRESS:
//                        FragmentUtil.getInstance().switchFragment(requireActivity(), personDataViewModel.addressListFragment, personDataViewModel.personDataFragment, R.id.content_container, com.swu.lib_common.R.anim.page_from_left_to_right_out);
//                        FragmentUtil.getInstance().switchFragment(requireActivity(), personDataViewModel.setTop, personDataViewModel.dataTop, R.id.top_container,com.swu.lib_common.R.anim.page_from_left_to_right_out);
//                        break;
//                    case TopFragmentType.FIX_ADDRESS:
//                        FragmentUtil.getInstance().switchFragment(requireActivity(), personDataViewModel.addAddressFragment, personDataViewModel.addressListFragment, R.id.content_container,com.swu.lib_common.R.anim.page_from_left_to_right_out);
//                        FragmentUtil.getInstance().switchFragment(requireActivity(), personDataViewModel.fixTop, personDataViewModel.setTop, R.id.top_container,com.swu.lib_common.R.anim.page_from_left_to_right_out);
//                        break;
//                    case TopFragmentType.ADD_ADDRESS:
//                        FragmentUtil.getInstance().switchFragment(requireActivity(), personDataViewModel.addAddressFragment, personDataViewModel.addressListFragment, R.id.content_container,com.swu.lib_common.R.anim.page_from_left_to_right_out);
//                        FragmentUtil.getInstance().switchFragment(requireActivity(), personDataViewModel.addTop, personDataViewModel.setTop, R.id.top_container,com.swu.lib_common.R.anim.page_from_left_to_right_out);
//                        break;
//                    default:
//                }
            }
        });

    }
//
//    @Nullable
//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        if (enter){
//            return AnimationUtils.loadAnimation(getContext(), com.swu.lib_common.R.anim.page_from_right_to_left_in);
//        }else {
//            return super.onCreateAnimation(transit, false,nextAnim);
//        }
//    }
}