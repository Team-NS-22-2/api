package com.mju.insuranceCompany.service.user.service;

import com.mju.insuranceCompany.service.user.controller.dto.UserBasicRequest;
import com.mju.insuranceCompany.service.user.domain.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "classpath:application.properties")
@Transactional
@Rollback(false)
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void createEmployeeUsers() {
        System.out.println("Start Create Data");
        userService.signUpEmployee(1, new UserBasicRequest("dev", "1234"), UserType.ROLE_DEV);
        userService.signUpEmployee(2, new UserBasicRequest("uw", "1234"), UserType.ROLE_UW);
        userService.signUpEmployee(3, new UserBasicRequest("comp", "1234"), UserType.ROLE_COMP);
        userService.signUpEmployee(4, new UserBasicRequest("sales", "1234"), UserType.ROLE_SALES);
        userService.signUpEmployee(5, new UserBasicRequest("devmanager", "1234"), UserType.ROLE_DEV);
        userService.signUpEmployee(6, new UserBasicRequest("uwmanager", "1234"), UserType.ROLE_UW);
        userService.signUpEmployee(7, new UserBasicRequest("compmanager", "1234"), UserType.ROLE_COMP);
        userService.signUpEmployee(8, new UserBasicRequest("salesmanager", "1234"), UserType.ROLE_SALES);
        userService.signUpEmployee(9, new UserBasicRequest("devmember", "1234"), UserType.ROLE_DEV);
        userService.signUpEmployee(10, new UserBasicRequest("uwmember", "1234"), UserType.ROLE_UW);
        userService.signUpEmployee(11, new UserBasicRequest("compmember", "1234"), UserType.ROLE_COMP);
        userService.signUpEmployee(12, new UserBasicRequest("salesmember", "1234"), UserType.ROLE_SALES);

    }
}