package com.mju.insuranceCompany.service.auth.service;

import com.mju.insuranceCompany.service.auth.controller.dto.AuthBasicRequest;
import com.mju.insuranceCompany.service.auth.domain.AuthType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("prod")
@Transactional
@Rollback(false)
class UserServiceTest {

    @Autowired
    AuthService userService;

//    @Test
    void createEmployeeUsers() {
        System.out.println("Start Create Data");
        userService.signUpEmployee(1, new AuthBasicRequest("dev", "1234"), AuthType.ROLE_DEV);
        userService.signUpEmployee(2, new AuthBasicRequest("uw", "1234"), AuthType.ROLE_UW);
        userService.signUpEmployee(3, new AuthBasicRequest("comp", "1234"), AuthType.ROLE_COMP);
        userService.signUpEmployee(4, new AuthBasicRequest("sales", "1234"), AuthType.ROLE_SALES);
        userService.signUpEmployee(5, new AuthBasicRequest("devmanager", "1234"), AuthType.ROLE_DEV);
        userService.signUpEmployee(6, new AuthBasicRequest("uwmanager", "1234"), AuthType.ROLE_UW);
        userService.signUpEmployee(7, new AuthBasicRequest("compmanager", "1234"), AuthType.ROLE_COMP);
        userService.signUpEmployee(8, new AuthBasicRequest("salesmanager", "1234"), AuthType.ROLE_SALES);
        userService.signUpEmployee(9, new AuthBasicRequest("devmember", "1234"), AuthType.ROLE_DEV);
        userService.signUpEmployee(10, new AuthBasicRequest("uwmember", "1234"), AuthType.ROLE_UW);
        userService.signUpEmployee(11, new AuthBasicRequest("compmember", "1234"), AuthType.ROLE_COMP);
        userService.signUpEmployee(12, new AuthBasicRequest("salesmember", "1234"), AuthType.ROLE_SALES);

    }
}