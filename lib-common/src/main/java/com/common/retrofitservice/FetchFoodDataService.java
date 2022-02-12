package com.common.retrofitservice;

import com.base.bean.FoodListPageBean;
import com.common.requestbase.ResponseModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chenxiong
 * date 1/24/22
 */
public interface FetchFoodDataService {

    @GET("menu/{floor}")
    Observable<ResponseModel<FoodListPageBean>> fetchFoodList(@Path("floor") int floor);

}
