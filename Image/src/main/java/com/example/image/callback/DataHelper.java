package com.example.image.callback;

import java.util.List;

/**
 * @author liwanlian
 * @date 2023/5/29 23:29
 */
public interface DataHelper <T>{
    boolean addAll(List<T> list);

    boolean addAll(int position, List<T> list);

    void add(T data);

    void add(int position, T data);

    void clear();

    boolean contains(T data);

    T getData(int index);

    void modify(T oldData, T newData);

    void modify(int index, T newData);

    boolean remove(T data);

    void remove(int index);
}
