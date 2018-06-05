package com.wang.platform.message.dao;

import java.util.List;


public interface BaseDao<E, I> {
    E selectById(I id);

    List<E> selectAll();

    void insert(E e);

    void del(I id);

    void update(E e);
}
