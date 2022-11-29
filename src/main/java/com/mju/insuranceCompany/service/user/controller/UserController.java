package com.mju.insuranceCompany.service.user.controller;

import com.mju.insuranceCompany.service.user.controller.dto.UserBasicRequest;
import com.mju.insuranceCompany.service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up/{cId}")
    public ResponseEntity signUp(@PathVariable int cId, @RequestBody UserBasicRequest request) {
        userService.signUp(cId, request);
        return ResponseEntity.noContent().build();
    }

}
