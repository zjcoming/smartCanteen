package com.base.dao;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName User
 * @Author zhangjun
 * @Date 2021/11/27 19:28
 * @Description User 数据模板
 */
//注解声明为Room模板
@Entity
public class UserBean implements Serializable{

    @PrimaryKey(autoGenerate = false)//主键是否自动增长，默认为false
    @NonNull
    private String uid;//token
    private String username;//用户姓名
    private String password;//用户密码
    private String telephone;//用户手机号
    private String profilePhoto;//用户头像
    private int score;//用户积分
    private Date createTime;//创建时间
    private int defaultAddress;//默认地址

    public UserBean(){

    }
    public UserBean(String telephone,String password){
        this.telephone = telephone;
        this.password = password;
    }
    public UserBean(String uid,String telephone,String password){
        this.uid = uid;
        this.telephone = telephone;
        this.password = password;
    }
    //以下是toString方法，构造方法和set，get方法
    public UserBean(String uid, String username, String password, String telephone, String profilePhoto, int score) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.telephone = telephone;
        this.profilePhoto = profilePhoto;
        this.score = score;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(int defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", score=" + score +
                '}';
    }
}
