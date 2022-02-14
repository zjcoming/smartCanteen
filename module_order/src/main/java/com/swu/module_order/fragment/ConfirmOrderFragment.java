package com.swu.module_order.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseFragment;
import com.common.bean.FoodItemBean;
import com.common.constants.RouteConstants;
import com.common.constants.TargetFragmentConstants;
import com.common.util.DialogUtil;
import com.swu.module_order.ChooseFoodActivity;
import com.swu.module_order.R;
import com.swu.module_order.adapter.ConfirmOrderGoodsAdapter;
import com.swu.module_order.databinding.FragmentConfirmOrderBinding;
import com.swu.module_order.decoration.GoodItemDecoration;
import com.swu.module_order.view_model.FoodPageViewModel;
import com.common.constants.DiningWay;

import java.util.ArrayList;
import java.util.List;

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
        //得到购物车中的菜品列表
        ChooseFoodActivity activity = (ChooseFoodActivity) getActivity();
        List<FoodItemBean> mDatas = activity.getBottomShopCartDetailDialog().getCarData();
        if(mDatas == null){
            mDatas = new ArrayList<>();
        }

        String foodImgUrl = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E7%BE%8E%E9%A3%9F%E5%9B%BE%E7%89%87&step_word=&hs=0&pn=1&spn=0&di=4180&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=574283538%2C544700096&os=1072085278%2C1418846723&simid=3346130196%2C380644665&adpicid=0&lpn=0&ln=1867&fr=&fmq=1644821953889_R&fm=&ic=undefined&s=undefined&hd=undefined&latest=undefined&copyright=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=https%3A%2F%2Fgimg2.baidu.com%2Fimage_search%2Fsrc%3Dhttp%3A%2F%2Fimg09.viwik.com%2Fimages%2F20180701%2Ftooopen_sy_052538253829965.jpg%26refer%3Dhttp%3A%2F%2Fimg09.viwik.com%26app%3D2002%26size%3Df9999%2C10000%26q%3Da80%26n%3D0%26g%3D0n%26fmt%3Djpeg%3Fsec%3D1647413957%26t%3D462fd4510f24dcf59d348965b41f956b&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3B3twgfi7_z%26e3Bv54AzdH3FrAzdH3Fmm0ulkd11lwd&gsm=2&rpstart=0&rpnum=0&islist=&querylist=&nojc=undefined&dyTabStr=MCwzLDYsNCw1LDgsMSw3LDIsOQ%3D%3D";

//        for (int i = 1; i <= 7; i++) {
//            mDatas.add(new FoodItemBean(i,"第i个",foodImgUrl,"大份","微辣",i,(float)i));
//        }

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
        Dialog successDialog = DialogUtil.showSelfDialog(getContext(), R.layout.confirm_order_buy_success_dialog);
        Dialog dialog = DialogUtil.showSelfDialog(getContext(),R.layout.confirm_order_buy_mode_dialog);
        dialog.show();

        LinearLayout wechat = dialog.findViewById(R.id.buy_mode_dialog_wechat);
        LinearLayout zhifubao = dialog.findViewById(R.id.buy_mode_dialog_zhifubao);
         if(wechat != null){
            wechat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    successDialog.show();
                    ImageView goBackToOrderPage = successDialog.findViewById(R.id.dialog_buysuccess_goback);

                    goBackToOrderPage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            successDialog.dismiss();
                            Log.v("ljh","点击了微信支付后的支付成功");
                            //getActivity().finish();
                            ARouter.getInstance().build(RouteConstants.Module_app.PAGER_NAVIGATION)
                                    .withString("testTargetFragment", TargetFragmentConstants.ORDER_FRAGMENT)
                                    .navigation();
                        }
                    });
                }
            });
        }
        if(zhifubao != null){
            zhifubao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    successDialog.show();
                    ImageView goBackToOrderPage = successDialog.findViewById(R.id.dialog_buysuccess_goback);

                    goBackToOrderPage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            successDialog.dismiss();
                            ARouter.getInstance().build(RouteConstants.Module_app.PAGER_NAVIGATION)
                                    .withString("targetFragment", TargetFragmentConstants.ORDER_FRAGMENT)
                                    .navigation();
                        }
                    });
                }
            });
        }
//        if(goBackToOrderPage != null){
//            Log.v("ljh","goBackToOrderPage不为null");
//            goBackToOrderPage.setOnClickListener(v -> ARouter.getInstance().build(RouteConstants.Module_app.PAGER_NAVIGATION)
//                    .withString("targetFragment", TargetFragmentConstants.ORDER_FRAGMENT)
//                    .navigation());
//        }

        successDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){//保证只执行一次 如果不加这个&& 则会执行两次
                    //如果用户按了返回键
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void initListener() {

    }
}