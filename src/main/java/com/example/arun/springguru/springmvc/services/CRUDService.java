package com.example.arun.springguru.springmvc.services;

import java.util.List;

public interface CRUDService<T> {

    List<?> listAll();

    T getById(Integer id);

    T saveOrUpdate(T t);

    void delete(Integer id);

}
