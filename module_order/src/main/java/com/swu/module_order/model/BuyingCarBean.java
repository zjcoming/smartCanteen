package com.swu.module_order.model;

/**
 * @ClassName BuyingCarBean
 * @Author zhangJun
 * @Date 2022/1/10 8:03 下午
 * @Description
 */
public class BuyingCarBean {
    //还没添加图片
    private String foodName;
    private String foodPrice;
    private String scale;
    private String favor;
    private int foodCount;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getFavor() {
        return favor;
    }

    public void setFavor(String favor) {
        this.favor = favor;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public BuyingCarBean(String foodName, String foodPrice, String scale, String favor, int foodCount) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.scale = scale;
        this.favor = favor;
        this.foodCount = foodCount;
    }

    @Override
    public String toString() {
        return "BuyingCarBean{" +
                "foodName='" + foodName + '\'' +
                ", foodPrice='" + foodPrice + '\'' +
                ", scale='" + scale + '\'' +
                ", favor='" + favor + '\'' +
                ", foodCount=" + foodCount +
                '}';
    }
}
