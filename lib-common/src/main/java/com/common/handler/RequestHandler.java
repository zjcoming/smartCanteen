package com.common.handler;

import com.base.bean.UserBean;
import com.common.requestbase.ResponseModel;
import com.common.constants.LoginAndRegisterConstants;
import com.common.retrofitservice.UserLoginService;
import com.common.util.RetrofitUtil;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 这里封装了全部的请求。
 */
public class RequestHandler {
    /**
     * 点击登录按钮
     * @param observer
     * @param userBean
     */
    public static void login(Observer<ResponseModel<HashMap<String,String>>> observer, UserBean userBean){
        UserLoginService userLoginService = RetrofitUtil.getService(UserLoginService.class, LoginAndRegisterConstants.BASE_URL);
        Observable<ResponseModel<HashMap<String,String>>> observable = userLoginService.login(userBean);
        observable.subscribeOn(Schedulers.newThread())//启动新线程 请求网络
                .observeOn(AndroidSchedulers.mainThread())//切换回主线程进行返回数据的处理
                .subscribe(observer);
    }

    /**
     * 手机验证码登录按钮
     * @param observer
     * @param userBean
     */
    public static void loginByPhone(Observer<ResponseModel<HashMap<String,String>>> observer, UserBean userBean){
        UserLoginService userLoginService = RetrofitUtil.getService(UserLoginService.class, LoginAndRegisterConstants.BASE_URL);
        Observable<ResponseModel<HashMap<String,String>>> observable = userLoginService.login(userBean);
        observable.subscribeOn(Schedulers.newThread())//启动新线程 请求网络
                .observeOn(AndroidSchedulers.mainThread())//切换回主线程进行返回数据的处理
                .subscribe(observer);
    }

    /**
     * 点击注册按钮
     * @param observer
     * @param userBean
     */
    public static void register(Observer<ResponseModel<HashMap<String,String>>> observer, UserBean userBean){
        UserLoginService userLoginService = RetrofitUtil.getService(UserLoginService.class, LoginAndRegisterConstants.BASE_URL);
        Observable<ResponseModel<HashMap<String,String>>> observable = userLoginService.register(userBean);
        observable.subscribeOn(Schedulers.newThread())//启动新线程 请求网络
                .observeOn(AndroidSchedulers.mainThread())//切换回主线程进行返回数据的处理
                .subscribe(observer);
    }

    /**
     * 忘记密码时的“完成”按钮
     * @param observer
     * @param userBean
     */
    public static void resetPwd(Observer<ResponseModel<HashMap<String,String>>> observer, UserBean userBean){
        UserLoginService userLoginService = RetrofitUtil.getService(UserLoginService.class, LoginAndRegisterConstants.BASE_URL);
        Observable<ResponseModel<HashMap<String,String>>> observable = userLoginService.resetPwd(userBean);
        observable.subscribeOn(Schedulers.newThread())//启动新线程 请求网络
                .observeOn(AndroidSchedulers.mainThread())//切换回主线程进行返回数据的处理
                .subscribe(observer);
    }
}
