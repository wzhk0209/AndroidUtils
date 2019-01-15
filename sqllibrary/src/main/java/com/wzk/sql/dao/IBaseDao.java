package com.wzk.sql.dao;

import java.util.List;

/**
 * Created by wangzhaokang on 2019/1/15.
 */

public interface IBaseDao<T> {
    Long insert(T entity);

    Long delete(T entity);

    Long update(T entity);

    List<T> find();
}
