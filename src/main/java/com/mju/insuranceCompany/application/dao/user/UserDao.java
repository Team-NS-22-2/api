package com.mju.insuranceCompany.application.dao.user;

import com.mju.insuranceCompany.application.dao.CrudInterface;
import com.mju.insuranceCompany.application.login.User;

public interface UserDao extends CrudInterface<User> {
    int login(String id, String password);
}
