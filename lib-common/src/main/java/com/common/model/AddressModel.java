package com.common.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    @PrimaryKey(autoGenerate = false)//主键是否自动增长，默认为false
    private String uid;//用户id

    public AddressModel(String campus, String address, String name, String telephone, int isDefault) {
        this.campus = campus;
        this.address = address;
        this.name = name;
        this.telephone = telephone;
        this.isDefault = isDefault;
    }

    public AddressModel(String uid, String campus, String address, String name, String telephone, int isDefault) {
        this.uid = uid;
        this.campus = campus;
        this.address = address;
        this.name = name;
        this.telephone = telephone;
        this.isDefault = isDefault;
    }

    private int id;//该条地址在后端数据库中id
    private String campus;//校区
    private String address;//地址（除校区外的地址）
    private String name;//用户姓名
    private String telephone;//用户手机号
    private int isDefault;//1是默认地址，0不是默认地址

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "AddressModel{" +
                "uid='" + uid + '\'' +
                ", id=" + id +
                ", campus='" + campus + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
