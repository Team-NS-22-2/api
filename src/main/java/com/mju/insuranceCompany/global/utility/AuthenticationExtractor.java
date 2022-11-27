package com.mju.insuranceCompany.global.utility;

import com.mju.insuranceCompany.service.user.domain.Users;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationExtractor {

    public static int extractCustomerIdByAuthentication() {
        return extractIdByAuthentication();
    }

    public static int extractEmployeeIdByAuthentication() {
        return extractIdByAuthentication();
    }

    private static int extractIdByAuthentication(){
        Users users = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return users.getRoleId();
    }
}
