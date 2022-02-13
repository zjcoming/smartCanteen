package com.swu.smartcanteen.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseFragment;
import com.base.UIUtils;
import com.bumptech.glide.Glide;
import com.common.constants.BaseUserInfo;
import com.common.constants.PermissionConstants;
import com.common.constants.RouteConstants;
import com.common.constants.TargetFragmentConstants;
import com.common.util.ImageUtil;
import com.common.util.PermissionUtil;
import com.swu.smartcanteen.application.CanteenApplication;
import com.swu.smartcanteen.R;
import com.swu.smartcanteen.databinding.FragmentUserBinding;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 刘金豪 on 2022/1/10
 * desc: 登录的Fragment
 */
@Route(path = RouteConstants.Module_app.PAGER_USER_FRAGMENT)
public class UserFragment extends BaseFragment<FragmentUserBinding> implements View.OnClickListener {
    private Bitmap userIconBitmap;//用户头像

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initALLViews();//初始化控件以及其点击事件
    }
    private void initALLViews(){
        //初始化头像
        initUserIcon();
        //初始化头像左边的欢迎textView
        initHelloTextView();
        //注册所有控件的点击事件
        registerAllOnclickListener();
    }
    /**
     * 初始化userIcon
     */
    public void initUserIcon(){
        //获取本地保存的用户头像
        userIconBitmap = ImageUtil.getPhotoFromStorage("userIcon");
        if (userIconBitmap == null){
            //说明本地没保存 需要从服务器加载用户头像
            //示例url：https://marsyr-210522.oss-cn-chengdu.aliyuncs.com/SWU_canteen/20220119114836258.jpg
            Glide.with(CanteenApplication.getContext()).load(BaseUserInfo.getUserBean().getProfilePhoto()).into(getBinding().myIcon);//从本地加载图片
        }else {
            Glide.with(CanteenApplication.getContext()).load(ImageUtil.getPhotoFromStorage("userIcon")).into(getBinding().myIcon);//从本地加载图片
        }
    }

    /**
     * 初始化欢迎TextView
     */
    public void initHelloTextView(){
        //查看是否登录
        if(true || BaseUserInfo.isIsLogin()){
            //已经登录，则更换名字
            getBinding().myUserName.setText("Hey! 屁眼峻！");
            getBinding().myBelowUserName.setText("今天是与你相遇的第n天");
        }
    }
    public void registerAllOnclickListener(){
        //注册用户头像的点击事件
        getBinding().myIcon.setOnClickListener(this::onClick);

        //注册下面四个菜单的点击事件
        getBinding().mySelfInfo.setOnClickListener(this::onClick);
        getBinding().myHistoryBuy.setOnClickListener(this::onClick);
        getBinding().myMsgCenter.setOnClickListener(this::onClick);
        getBinding().mySelfLove.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_icon:
                showTypeDialog();
                break;
            case R.id.my_self_info:
                clickMySelfInfo();
                break;
            case R.id.my_history_buy:
                clickMyHistoryBuy();
                break;
            case R.id.my_msg_center:
                clickMyMsgCenter();
                break;
            case R.id.my_self_love:
                clickMySelfLove();
                break;
            default:
                break;
        }
    }

    private void clickMySelfInfo(){
        UIUtils.INSTANCE.showToast(CanteenApplication.getContext(),"您点击了个人信息");
        getActivity().finish();
        ARouter.getInstance().build(RouteConstants.Module_person.PAGER_UI_ACTIVITY)
                .withString("targetFragment", TargetFragmentConstants.DATA_PERSON)
                .navigation();
    }
    private void clickMyHistoryBuy(){
        UIUtils.INSTANCE.showToast(CanteenApplication.getContext(),"您点击了历史订单");
    }
    private void clickMyMsgCenter(){
        UIUtils.INSTANCE.showToast(CanteenApplication.getContext(),"您点击了消息中心");
        getActivity().finish();
        ARouter.getInstance().build(RouteConstants.Module_person.PAGER_UI_ACTIVITY)
                .withString("targetFragment", TargetFragmentConstants.MESSAGE_FRAGMENT)
                .navigation();
//        Fragment messageFragment = (Fragment)ARouter.getInstance().build(RouteConstants.Module_person.PAGER_MESSAGE_FRAGMENT).navigation();
//        FragmentUtil.getInstance().startFragment(getActivity(),messageFragment, com.swu.module_person.R.id.container);
    }
    private void clickMySelfLove(){
        UIUtils.INSTANCE.showToast(CanteenApplication.getContext(),"您点击了我的喜爱");
    }



    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        View view = View.inflate(getActivity(), R.layout.user_icon_click_dialog, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                //检查相机权限
                PermissionUtil.requestOtherPermissions(getContext(), PermissionConstants.camera, new PermissionUtil.PermissionRequestSuccessCallBack() {
                    @Override
                    public void onHasPermission() {
                        //权限获取到了
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "userIcon.jpg")));
                        startActivityForResult(intent2, 2);// 采用ForResult打开
                        dialog.dismiss();
                    }
                });
//
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/userIcon.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    userIconBitmap = extras.getParcelable("data");
                    if (userIconBitmap != null) {
                        //保存到本地
                        ImageUtil.savePhotoToStorage(userIconBitmap,"userIcon");
                        //保存到服务器
                        ImageUtil.savePhotoToServer("userIcon");
                        //显示到头像上
                        getBinding().myIcon.setImageBitmap(userIconBitmap);
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListener() {

    }
}