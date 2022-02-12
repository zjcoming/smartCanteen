package com.swu.module_order.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.base.BaseFragment;
import com.common.util.DialogUtil;
import com.swu.module_order.R;
import com.swu.module_order.adapter.ConfirmOrderGoodsAdapter;
import com.swu.module_order.databinding.FragmentConfirmOrderBinding;
import com.swu.module_order.decoration.GoodItemDecoration;
import com.swu.module_order.view_model.FoodPageViewModel;
import com.common.constants.DiningWay;

import java.util.ArrayList;

/**
 * Created by 刘金豪 on 2021/1/11
 * desc: 确定订单页面
 */
public class ConfirmOrderFragment extends BaseFragment<FragmentConfirmOrderBinding> implements View.OnClickListener {

    private FoodPageViewModel foodPageViewModel;

    public ConfirmOrderFragment(Context context){

    }

    public ConfirmOrderFragment(FoodPageViewModel pageViewModel) {
        this.foodPageViewModel = pageViewModel;
    }

    @Override
    public void onStart() {
        super.onStart();
        foodPageViewModel.getShopCart().setSettlePayCallBack(() -> {
            Log.e("cx","点击了结算页面");
            showBuyMode();
            return null;
        });
        foodPageViewModel.getShopCart().setGoPayText("提交订单");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        foodPageViewModel.getShopCart().setSettlePayCallBack(null);
        foodPageViewModel.getShopCart().setGoPayText("去结算");
    }

    @Override
    public void initViews() {
        //初始化商品列表
        initGoods();
        getBinding().confirmOrderBuymode.setOnClickListener(this);
        getBinding().confirmOrderBuymode.setVisibility(View.GONE);
        int imgEatModeRes;
        switch (foodPageViewModel.getDiningWay()) {
            case DiningWay.TAKE_OUT:
                imgEatModeRes = R.drawable.confirm_order_top_take_out;
                getBinding().confirmOrderMsgLinearFirstText.setText("寝室号");
                getBinding().confirmOrderMsgLinearThirdText.setText("送达时间");
                break;
            case DiningWay.PRE_ORDER:
                imgEatModeRes = R.drawable.confirm_order_top_before;
                getBinding().confirmOrderMsgLinearFirstText.setText("座位号");
                getBinding().confirmOrderMsgLinearThirdText.setText("取餐时间");
                break;
            default:
                imgEatModeRes = R.drawable.confirm_order_top_in_canteen;
                getBinding().confirmOrderMsgLinearFirstText.setText("座位号");
                getBinding().confirmOrderMsgLinearThirdText.setText("取餐时间");
        }
        getBinding().confirmOrderEatMode.setImageResource(imgEatModeRes);
    }
    public void initGoods(){
        ArrayList<String> mDatas = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            mDatas.add("第 " + i + " 个item");
        }

        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        getBinding().confirmOrderRecyclerview.setLayoutManager(linearLayoutManager);

        ConfirmOrderGoodsAdapter adapter = new ConfirmOrderGoodsAdapter(getContext(), mDatas);
        getBinding().confirmOrderRecyclerview.setAdapter(adapter);
        getBinding().confirmOrderRecyclerview.addItemDecoration(new GoodItemDecoration());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
    }

    /**
     * 用户选择支付方式
     */
    public void showBuyMode(){
        Dialog dialog = DialogUtil.showSelfDialog(getContext(),R.layout.confirm_order_buy_mode_dialog);
        View wechat = dialog.findViewById(R.id.buy_mode_dialog_wechat);
        View zhifubao = dialog.findViewById(R.id.buy_mode_dialog_zhifubao);
//        wechat.setOnClickListener(v -> UIUtils.INSTANCE.showToast(getContext(),"微信支付"));
//        zhifubao.setOnClickListener(v -> UIUtils.INSTANCE.showToast(getContext(),"支付宝支付"));
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){//保证只执行一次 如果不加这个&& 则会执行两次
                    //如果用户按了返回键
//                    UIUtils.INSTANCE.showToast(getContext(),"您取消了支付");
                }
                return false;
            }
        });
        dialog.show();
    }

    @Override
    public void initListener() {

    }
}