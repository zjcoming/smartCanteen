package com.common.model;

import androidx.room.Entity;

import java.io.Serializable;

/**
 * @ClassName AddressListModel
 * @Author zhangJun
 * @Date 2022/1/18 5:41 下午
 * @Description
 */
//注解声明为Room模板
@Entity
public class AddressModel implements Serializable {

    private String uid;
    private String campus;//校区
    private String address;//地址（除校区外的地址）
    private String name;//用户姓名
    private String phone;//用户手机号
    private int isDefaultAddress;

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIsDefaultAddress() {
        return isDefaultAddress;
    }

    public void setIsDefaultAddress(int isDefaultAddress) {
        this.isDefaultAddress = isDefaultAddress;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
