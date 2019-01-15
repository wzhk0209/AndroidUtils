package com.wzk.sql.dao;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * Created by wangzhaokang on 2019/1/15.
 */

public class SqlDaoFactory {

    private static final SqlDaoFactory ourInstance = new SqlDaoFactory();
    private final SQLiteDatabase database;

    public static SqlDaoFactory getInstance() {
        return ourInstance;
    }

    private SqlDaoFactory() {

        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        database = SQLiteDatabase.openOrCreateDatabase(absolutePath + "/dongnao.db", null);

    }

    public synchronized <T> BaseDao<T> getSqlDao(Class<T> entityClass){
        BaseDao<T> baseDao = null;
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(entityClass,database);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return baseDao;
    }

}
