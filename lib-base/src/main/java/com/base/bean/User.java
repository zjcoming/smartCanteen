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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }



    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
    public User(int uid, String username, String telephone, String profilePhoto, int score) {
        this.uid = uid;
        this.username = username;
        this.telephone = telephone;
        this.profilePhoto = profilePhoto;
        this.score = score;
    }
}
