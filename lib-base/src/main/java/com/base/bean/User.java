package com.base.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * @ClassName User
 * @Author zhangjun
 * @Date 2021/11/27 19:28
 * @Description User 数据模板
 */
//注解声明为Room模板
@Entity
public class User implements Serializable {

    @PrimaryKey(autoGenerate = false)//主键是否自动增长，默认为false
    private int uid;
    private String username;
    //不需要密码？
//    private String password;
    private String telephone;
    private String profilePhoto;
    private int score;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProfile_photo() {
        return profilePhoto;
    }

    public void setProfile_photo(String profile_photo) {
        this.profilePhoto = profile_photo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", userName='" + username + '\'' +
                ", telephone='" + telephone + '\'' +
                ", profile_photo='" + profilePhoto + '\'' +
                ", score=" + score +
                '}';
    }

    //全构造器
    public User(int uid, String userName, String telephone, String profile_photo, int score) {
        this.uid = uid;
        this.username = userName;
        this.telephone = telephone;
        this.profilePhoto = profile_photo;
        this.score = score;
    }

}
