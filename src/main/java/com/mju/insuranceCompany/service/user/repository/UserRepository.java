package com.mju.insuranceCompany.service.user.repository;

import com.mju.insuranceCompany.service.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByUserId(String userId);
}
