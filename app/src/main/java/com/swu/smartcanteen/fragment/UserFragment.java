package com.swu.smartcanteen.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.base.BaseFragment;
import com.swu.smartcanteen.R;
import com.swu.smartcanteen.databinding.FragmentUserBinding;

/**
 * created by zhangjun
 * data 2021/11/27
 * des:
 */

public class UserFragment extends Fragment {

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }
}