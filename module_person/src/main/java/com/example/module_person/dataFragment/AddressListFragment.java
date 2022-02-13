package com.example.module_person.dataFragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.base.BaseFragment;
import com.common.model.AddressModel;
import com.common.selfview.MyTitleBar;
import com.base.FragmentUtil;
import com.common.util.LogUtil;
import com.example.module_person.adapter.AddressListAdapter;
import com.example.module_person.adapter.TestBean;
import com.example.module_person.viewmodel.PersonDataViewModel;
import com.swu.module_person.R;
import com.swu.module_person.databinding.FragmentAddressListBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class AddressListFragment extends BaseFragment<FragmentAddressListBinding> {

    private PersonDataViewModel personDataViewModel;
    private AddressListAdapter adapter;
    private ArrayList<AddressModel> realAddressList;

    @Override
    public void initViews() {

        personDataViewModel = new ViewModelProvider(requireActivity()).get(PersonDataViewModel.class);
        realAddressList = new ArrayList<>();
        registerAddressData();
        setCurAddress();
        registerObserver();
    }

    /**
     * 设置当前地址
     */
    private void setCurAddress() {
        LogUtil.e("进入1");
        if (personDataViewModel.listData.getValue() != null && personDataViewModel.listData.getValue().size() > 0) {
            LogUtil.e("进入2");
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_address, null);
            TextView campus = view.findViewById(R.id.address_campus);
            TextView address = view.findViewById(R.id.address_detail);
            TextView name = view.findViewById(R.id.address_user_name);
            TextView phone = view.findViewById(R.id.address_phone_number);
            ImageButton edit = view.findViewById(R.id.address_edit);
            campus.setText(personDataViewModel.listData.getValue().get(0).getCampus());
            address.setText(personDataViewModel.listData.getValue().get(0).getAddress());
            name.setText(personDataViewModel.listData.getValue().get(0).getName());
            phone.setText(personDataViewModel.listData.getValue().get(0).getTelephone());
            //显示
            getBinding().addressCurrent.addView(view);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    personDataViewModel.userName.setValue(personDataViewModel.listData.getValue().get(0).getName());
                    personDataViewModel.userPhone.setValue(personDataViewModel.listData.getValue().get(0).getTelephone());
                    personDataViewModel.userAddress.setValue(personDataViewModel.listData.getValue().get(0).getAddress());
                    FragmentUtil.getInstance().switchFragment(requireActivity(), personDataViewModel.addressListFragment, personDataViewModel.fixAddressFragment, R.id.container, com.swu.lib_common.R.anim.page_from_right_to_left_in);
                }
            });
        }
    }

    /**
     * 地址列表数据
     */
    private void registerAddressData() {
        personDataViewModel.getAddressListFromServer();
        getBinding().rvAddress.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        //初始化否则报错
        personDataViewModel.listData.setValue(new ArrayList<>());
        adapter = new AddressListAdapter(realAddressList, getContext());
        //点击修改地址按钮事件回调
        adapter.setOnFixAddressListener(new AddressListAdapter.FixAddressCallback() {
            @Override
            public void onFixClick(int position) {
                personDataViewModel.userName.setValue(realAddressList.get(position).getName());
                personDataViewModel.userPhone.setValue(realAddressList.get(position).getTelephone());
                personDataViewModel.userAddress.setValue(realAddressList.get(position).getAddress());
                FragmentUtil.getInstance().switchFragment(requireActivity(), personDataViewModel.addressListFragment, personDataViewModel.fixAddressFragment, R.id.container, com.swu.lib_common.R.anim.page_from_right_to_left_in);
            }
        });
        getBinding().rvAddress.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
        getBinding().rvAddress.setAdapter(adapter);
    }

    private void registerObserver() {
        personDataViewModel.listData.observe(this, new Observer<ArrayList<AddressModel>>() {
            @Override
            public void onChanged(ArrayList<AddressModel> addressModels) {
                if (addressModels == null){
                    realAddressList.clear();
                }else{
                    realAddressList.addAll(addressModels);
                }
                if (realAddressList.size() > 0){
                    realAddressList.remove(0);
                }
                adapter.setDatas(realAddressList);
                //从新设置当前地址
                setCurAddress();
                //刷新recyclerView
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initListener() {

        getBinding().btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentUtil.getInstance().switchFragment(requireActivity(), personDataViewModel.addressListFragment, personDataViewModel.addAddressFragment, R.id.container, com.swu.lib_common.R.anim.page_from_right_to_left_in);
            }
        });
        onBackClick();
    }

    private void onBackClick() {
        getBinding().btBack.setOnMyTitleBarListener(new MyTitleBar.OnMyTitleBarListener() {
            @Override
            public void onLeftClick() {
                FragmentUtil.getInstance().switchFragment(requireActivity(), personDataViewModel.addressListFragment, personDataViewModel.personDataFragment, R.id.container, com.swu.lib_common.R.anim.page_from_left_to_right_out);
            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }
}