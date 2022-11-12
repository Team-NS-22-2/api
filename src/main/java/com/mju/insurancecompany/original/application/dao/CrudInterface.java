package com.mju.insurancecompany.original.application.dao;

public interface CrudInterface<E> {
    void create(E e);
    E read(int id);
    boolean update(int id);
    boolean delete(int id);
}
