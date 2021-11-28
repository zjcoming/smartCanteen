package com.base.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.base.bean.UserBean;
import com.base.dao.UserDao;

/**
 * @ClassName CanteenDatabase
 * @Author zhangjun
 * @Date 2021/11/27 19:48
 * @Description 　　　　　　　　　　　　数据库单例
 *
 * 数据库使用，如果需要另外的Dao请在下面代码中添加
 * ！！！！数据库的所有操作必须在新开线程执行，不能在ＵＩ线程否则会报错！！！！
 *
 *
 *         Thread(Runnable {
 *             CanteenDatabase.getInstance(application的context对象).userDao.insert(添加的对象必须是添加Entity注解)
 *         }).start()
 *
 *         关于增删改查的几种方式写在BaseDao中
 */
@Database(entities = { UserBean.class }, version = 1,exportSchema = false)
public abstract class CanteenDatabase extends RoomDatabase {

    //生命UserDao
    public abstract UserDao getUserDao();

    private static final String DB_NAME = "CanteenDatabase.db";
    private static volatile CanteenDatabase instance;

    public static synchronized CanteenDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static CanteenDatabase  create(final Context context) {
        return Room.databaseBuilder(
                context,
                CanteenDatabase.class,
                DB_NAME).build();
    }


}
