package com.example.module_person.viewmodel;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.base.BaseViewModel;
import com.common.constants.BaseUserInfo;
import com.common.model.AddressModel;
import com.common.repository.UserRepository;
import com.common.requestbase.AppObserver;
import com.common.requestbase.ResponseModel;
import com.common.util.LogUtil;
import com.example.module_person.dataFragment.AddAddressFragment;
import com.example.module_person.dataFragment.AddressListFragment;
import com.example.module_person.dataFragment.FixAddressFragment;
import com.example.module_person.dataFragment.PersonDataFragment;
import com.example.module_person.widget.TopBarFragment;
import com.example.module_person.widget.TopFragmentType;

import java.util.ArrayList;

/**
 * @ClassName PersonDataViewModel
 * @Author zhangJun
 * @Date 2022/1/14 10:52 下午
 * @Description
 */
public class PersonDataViewModel extends BaseViewModel implements LifecycleObserver {
    public AddressListFragment addressListFragment = new AddressListFragment();
    public PersonDataFragment personDataFragment = new PersonDataFragment();
    public AddAddressFragment addAddressFragment = new AddAddressFragment();
    public FixAddressFragment fixAddressFragment = new FixAddressFragment();
    public MutableLiveData<ArrayList<AddressModel>> listData = new MutableLiveData<>();
    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> userPhone = new MutableLiveData<>();
    public MutableLiveData<String> userAddress = new MutableLiveData<>();

    public LiveData<String> getUserName() {
        return userName;
    }


    public LiveData<String> getUserPhone() {
        return userPhone;
    }


    public LiveData<String> getUserAddress() {
        return userAddress;
    }

    //获取用户地址列表信息
    public void getAddressListFromServer(){
        UserRepository.getUserRepository().getUserAddressListFromServer(new AppObserver<ResponseModel<ArrayList<AddressModel>>>() {
            @Override
            public void onData(@NonNull ResponseModel<ArrayList<AddressModel>> o) {
                if (o == null){
                    return;
                }
                listData.setValue(o.getData());
                LogUtil.e("获取成功");
            }
        }, BaseUserInfo.getUserBean().getUid());
    }


}
