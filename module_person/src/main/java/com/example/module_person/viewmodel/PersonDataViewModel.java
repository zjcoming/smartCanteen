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
    public TopBarFragment dataTop = TopBarFragment.getInstance(TopFragmentType.PERSON_DATA);
    public TopBarFragment setTop = TopBarFragment.getInstance(TopFragmentType.SET_ADDRESS);
    public TopBarFragment addTop = TopBarFragment.getInstance(TopFragmentType.ADD_ADDRESS);
    public TopBarFragment fixTop = TopBarFragment.getInstance(TopFragmentType.FIX_ADDRESS);
    public AddressListFragment addressListFragment = new AddressListFragment();
    public PersonDataFragment personDataFragment = new PersonDataFragment();
    public AddAddressFragment addAddressFragment = new AddAddressFragment();
}
