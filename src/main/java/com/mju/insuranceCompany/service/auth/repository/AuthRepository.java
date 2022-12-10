package com.mju.insuranceCompany.service.auth.repository;

import com.mju.insuranceCompany.service.auth.domain.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AuthRepository extends JpaRepository<Auth, Integer> {
    Optional<Auth> findByUserId(String userId);
}
