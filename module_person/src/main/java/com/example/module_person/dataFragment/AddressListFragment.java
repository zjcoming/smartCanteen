package com.example.module_person.dataFragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;

import com.base.BaseFragment;
import com.common.selfview.MyTitleBar;
import com.base.util.FragmentUtil;
import com.example.module_person.adapter.AddressListAdapter;
import com.example.module_person.adapter.TestBean;
import com.example.module_person.viewmodel.PersonDataViewModel;
import com.swu.module_person.R;
import com.swu.module_person.databinding.FragmentAddressListBinding;

import java.util.ArrayList;

public class AddressListFragment extends BaseFragment<FragmentAddressListBinding> {

    private PersonDataViewModel personDataViewModel;

    private ArrayList<TestBean> testDatas;
    @Override
    public void initViews() {

        testDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            testDatas.add(new TestBean("荣昌校区","D栋111","陈雄是nt","123456789"));
        }
        getBinding().rvAddress.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        AddressListAdapter adapter = new AddressListAdapter(testDatas,getContext());
        getBinding().rvAddress.addItemDecoration(new DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL));
        getBinding().rvAddress.setAdapter(adapter);




        personDataViewModel = new ViewModelProvider(requireActivity()).get(PersonDataViewModel.class);
//        View view = LayoutInflater.from(getContext()).inflate((XmlPullParser) getBinding().addressCurrent,getBinding().getRoot(),false);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_address, null);
        getBinding().addressCurrent.addView(view);
    }

    @Override
    public void initListener() {

        getBinding().btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentUtil.getInstance().switchFragment(requireActivity(), personDataViewModel.addressListFragment, personDataViewModel.addAddressFragment, R.id.content_container, com.swu.lib_common.R.anim.page_from_right_to_left_in);
            }
        });
        onBackClick();
    }

    private void onBackClick() {
        getBinding().btBack.setOnMyTitleBarListener(new MyTitleBar.OnMyTitleBarListener() {
            @Override
            public void onLeftClick() {
                FragmentUtil.getInstance().switchFragment(requireActivity(),personDataViewModel.addressListFragment,personDataViewModel.personDataFragment, R.id.content_container, com.swu.lib_common.R.anim.page_from_left_to_right_out);
            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }
}