package com.common.api;


import com.base.ApplicationContext;
import com.base.util.UIUtils;
import com.common.constants.BaseAppConstants;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 自定义Observer
 */
public abstract class AppObserver<T> implements Observer {

    public abstract void onData(@NonNull T o);
    public void onFailed(@NonNull Throwable e){
        e.printStackTrace();
        UIUtils.INSTANCE.showToast(ApplicationContext.getContext(),"网络异常，请稍后重试");
    };
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(Object o) {
        onData((T)o);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFailed(e);
    }

    @Override
    public void onComplete() {

    }
}
