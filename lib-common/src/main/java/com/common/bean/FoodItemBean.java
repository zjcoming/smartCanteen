package com.common.bean;

/**
 * Created by 刘金豪 on 2022/2/14
 * desc: 在RecyclerView中的Food的bean
 */
public class FoodItemBean {
    int id;
    String foodName;
    String foodImgUrl;
    String foodConsumption;//分量
    String foodFlavor;//口味
    int foodCount;
    Float foodPrice;

    public FoodItemBean(int id, String foodName, String foodImgUrl, String foodConsumption, String foodFlavor, int foodCount, Float foodPrice) {
        this.id = id;
        this.foodName = foodName;
        this.foodImgUrl = foodImgUrl;
        this.foodConsumption = foodConsumption;
        this.foodFlavor = foodFlavor;
        this.foodCount = foodCount;
        this.foodPrice = foodPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImgUrl() {
        return foodImgUrl;
    }

    public void setFoodImgUrl(String foodImgUrl) {
        this.foodImgUrl = foodImgUrl;
    }

    public String getFoodConsumption() {
        return foodConsumption;
    }

    public void setFoodConsumption(String foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    public String getFoodFlavor() {
        return foodFlavor;
    }

    public void setFoodFlavor(String foodFlavor) {
        this.foodFlavor = foodFlavor;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public Float getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Float foodPrice) {
        this.foodPrice = foodPrice;
    }
}
