package com.example.module_person.viewmodel;


import com.base.BaseViewModel;
import com.example.module_person.dataFragment.AddAddressFragment;
import com.example.module_person.dataFragment.AddressListFragment;
import com.example.module_person.dataFragment.PersonDataFragment;
import com.example.module_person.widget.TopBarFragment;
import com.example.module_person.widget.TopFragmentType;

/**
 * @ClassName PersonDataViewModel
 * @Author zhangJun
 * @Date 2022/1/14 10:52 下午
 * @Description
 */
public class PersonDataViewModel extends BaseViewModel {
    public AddressListFragment addressListFragment = new AddressListFragment();
    public PersonDataFragment personDataFragment = new PersonDataFragment();
    public AddAddressFragment addAddressFragment = new AddAddressFragment();
}
