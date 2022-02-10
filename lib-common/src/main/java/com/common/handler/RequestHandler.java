package com.common.handler;

import com.base.bean.FoodListPageBean;
import com.base.dao.UserBean;
import com.common.constants.LoginAndRegisterConstants;
import com.common.model.MessageModel;
import com.common.requestbase.ResponseModel;
import com.common.retrofitservice.FetchFoodDataService;
import com.common.retrofitservice.UserInfoService;
import com.common.retrofitservice.UserLoginService;
import com.common.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 这里封装了全部的请求。
 */
public class RequestHandler {
    /**
     * 更改消息属性
     */
    public static void setMessage(Observer<ResponseModel<HashMap<String,String>>> observer, MessageModel messageModel){
        UserInfoService userInfoService = RetrofitUtil.getService(UserInfoService.class, LoginAndRegisterConstants.BASE_URL);
        Observable<ResponseModel<HashMap<String,String>>> observable = userInfoService.setMessage(messageModel);
        observable.subscribeOn(Schedulers.newThread())//启动新线程 请求网络
                .observeOn(AndroidSchedulers.mainThread())//切换回主线程进行返回数据的处理
                .subscribe(observer);
    }
    /**
     * 获取用户消息列表
     */
    public static void getUserMessages(Observer<ResponseModel<ArrayList<MessageModel>>> observer, String uid){
        UserInfoService userInfoService = RetrofitUtil.getService(UserInfoService.class, LoginAndRegisterConstants.BASE_URL);
        Observable<ResponseModel<ArrayList<MessageModel>>> observable = userInfoService.getUserMessages(uid);
        observable.subscribeOn(Schedulers.newThread())//启动新线程 请求网络
                .observeOn(AndroidSchedulers.mainThread())//切换回主线程进行返回数据的处理
                .subscribe(observer);
    }
    /**
     * 设置用户头像
     */
    public static void setUserPhoto(Observer<ResponseModel<HashMap<String,String>>> observer, RequestBody requestBody){
        UserInfoService userInfoService = RetrofitUtil.getService(UserInfoService.class, LoginAndRegisterConstants.BASE_URL);
        Observable<ResponseModel<HashMap<String,String>>> observable = userInfoService.setUserPhoto(requestBody);
        observable.subscribeOn(Schedulers.newThread())//启动新线程 请求网络
                .observeOn(AndroidSchedulers.mainThread())//切换回主线程进行返回数据的处理
                .subscribe(observer);
    }
    /**
     * 获取用户的所有数据
     */
    public static void getUserInfo(Observer<ResponseModel<UserBean>> observer, String uid){
        UserInfoService userInfoService = RetrofitUtil.getService(UserInfoService.class, LoginAndRegisterConstants.BASE_URL);
        Observable<ResponseModel<UserBean>> observable = userInfoService.getUserInfo(uid);
        observable.subscribeOn(Schedulers.newThread())//启动新线程 请求网络
                .observeOn(AndroidSchedulers.mainThread())//切换回主线程进行返回数据的处理
                .subscribe(observer);
    }

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

    public static void fetchFoodList(Observer<ResponseModel<FoodListPageBean>> observer, int floor) {
        FetchFoodDataService foodDataService = RetrofitUtil.getService(FetchFoodDataService.class, LoginAndRegisterConstants.BASE_URL);
        Observable<ResponseModel<FoodListPageBean>> observable = foodDataService.fetchFoodList(floor);
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
