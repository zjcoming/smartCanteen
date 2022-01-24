package com.common.retrofitservice;

import com.base.bean.FoodListBean;
import com.common.requestbase.ResponseModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chenxiong
 * date 1/24/22
 */
public interface FetchFoodDataService {

    @GET("menu")
    Observable<ResponseModel<FoodListBean>> fetchFoodList(@Query("floor") int floor);

}
