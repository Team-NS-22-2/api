package com.mju.insuranceCompany.application.repository;

import com.mju.insuranceCompany.application.login.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
