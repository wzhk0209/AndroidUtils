package com.wzk.sql.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wzk.sql.annotation.DbFiled;
import com.wzk.sql.annotation.DbTable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by wangzhaokang on 2019/1/15.
 */

public class BaseDao<T> implements IBaseDao<T> {

    private SQLiteDatabase database;
    private boolean isInit = false;
    private Class<T> entityClass;
    private String mTableName;

    private HashMap<String, Field> mCacheMap;

    @Override
    public Long insert(T entity) {

        ContentValues contentValues = getContentValues(entity);
        long insert = database.insert(mTableName, null, contentValues);
        return insert;
    }

    @Override
    public Long delete(T entity) {

//        int delete = database.delete(mTableName, , new String[]{});
        return 0L;
    }

    @Override
    public Long update(T entity) {
        return null;
    }

    @Override
    public List<T> find() {
        return null;
    }

    protected synchronized boolean init(Class<T> entity, SQLiteDatabase sqLiteDatabase) {

        if (!isInit) {
            entityClass = entity;
            database = sqLiteDatabase;
            mTableName = entity.getAnnotation(DbTable.class).value();

            if (!database.isOpen())
                return false;

            if (!autoCreateTable())
                return false;

            isInit = true;
        }
        initCacheMap();
        return isInit;
    }

    private void initCacheMap() {
        mCacheMap = new HashMap<>();
        Cursor cursor = null;
        String sql = "select * from " + this.mTableName + " limit 1,0";
        try {
            cursor = database.rawQuery(sql, null);
            String[] columnNames = cursor.getColumnNames();
            Field[] fields = entityClass.getDeclaredFields();
            for (String columnName : columnNames) {

                Field resultField = null;
                for (Field field : fields) {
                    if (columnName.equals(field.getAnnotation(DbFiled.class).value())) {
                        resultField = field;
                        break;
                    }
                }

                if (resultField != null)
                    mCacheMap.put(columnName, resultField);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }


    }

    private boolean autoCreateTable() {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CREATE TABLE IF NOT EXISTS ")
                .append(mTableName + " (");

        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            Class type = field.getType();

            if (type == String.class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " TEXT,");
            } else if (type == Double.class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " DOUBLE,");
            } else if (type == Integer.class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " INTEGER,");
            } else if (type == Long.class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " BIGINT,");
            } else if (type == byte[].class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " BLOB,");
            } else {
                continue;
            }
        }

        if (stringBuffer.charAt(stringBuffer.length() - 1) == ',') {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }

        stringBuffer.append(")");

        try {
            this.database.execSQL(stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private ContentValues getContentValues(T entity) {
        ContentValues contentValues = new ContentValues();
        Iterator<Map.Entry<String, Field>> iterator = mCacheMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Field> fieldEntry = iterator.next();
            Field field = fieldEntry.getValue();
            String key = fieldEntry.getKey();

            field.setAccessible(true);
            try {
                Object o = field.get(entity);
                Class<?> type = field.getType();
                if (type == String.class) {
                    String value = (String)o;
                    contentValues.put(key,value);
                } else if (type == Double.class) {
                    Double value = (Double)o;
                    contentValues.put(key,value);
                } else if (type == Integer.class) {
                    Integer value = (Integer)o;
                    contentValues.put(key,value);
                } else if (type == Long.class) {
                    Long value = (Long)o;
                    contentValues.put(key,value);
                } else if (type == byte[].class) {
                    byte[] value = (byte[])o;
                    contentValues.put(key,value);
                } else {
                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return contentValues;
    }
}
