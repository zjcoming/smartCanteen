package com.swu.module_order.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.BaseFragment;
import com.common.util.LogUtil;
import com.swu.module_order.R;
import com.swu.module_order.adapter.BuyingCarAdapter;
import com.swu.module_order.databinding.FragmentBuyingCarBinding;
import com.swu.module_order.model.BuyingCarBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BuyingCarFragment
 * @Author zhangJun
 * @Date 2022/1/10 7:35 下午
 * @Description
 */
public class BuyingCarFragment extends BaseFragment<FragmentBuyingCarBinding> {
    private List<BuyingCarBean> carData = new ArrayList<>();
    RecyclerView recyclerView;
    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation;
        if (enter){
            animation = AnimationUtils.loadAnimation(getContext(),R.anim.buyingcar_in);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());//速度曲线，先加速后减速
        }else {
            animation = AnimationUtils.loadAnimation(getContext(),R.anim.buyingcar_out);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());//速度曲线，先加速后减速
        }
        return animation;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initListener() {
        //模拟数据
        for (int i = 1; i < 21; i++) {
            carData.add(new BuyingCarBean("水煮肉片"+i,"￥18.88","小份","微辣",1));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(),
                LinearLayoutManager.VERTICAL,false));

        BuyingCarAdapter carAdapter = new BuyingCarAdapter(carData,getContext());
        carAdapter.setOnClickListener(new BuyingCarAdapter.OnClickCallback() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "[onItemClick]+[position] = "+position, Toast.LENGTH_SHORT).show();
                LogUtil.e("[onItemClick]");
                carAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAddItemClick(View view, int position) {
                LogUtil.e("[onAddItemClick]");
                Toast.makeText(getActivity(), "[onAddItemClick]+[position] = "+position, Toast.LENGTH_SHORT).show();
                carAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSubItemClick(View view, int position) {
                LogUtil.e("[onSubItemClick]");
                Toast.makeText(getActivity(), "[onSubItemClick]+[position] = "+position, Toast.LENGTH_SHORT).show();
                carAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(carAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_buying_car,container,false);
        recyclerView = root.findViewById(R.id.rv_buy_car);
        return root;
    }
}
