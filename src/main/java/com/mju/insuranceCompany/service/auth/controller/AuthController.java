package com.mju.insuranceCompany.service.auth.controller;

import com.mju.insuranceCompany.service.auth.controller.dto.AuthBasicRequest;
import com.mju.insuranceCompany.service.auth.applicationservice.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService userService;

    @PostMapping("/sign-up/{cId}")
    public ResponseEntity signUp(@PathVariable int cId, @RequestBody AuthBasicRequest request) {
        userService.signUp(cId, request);
        return ResponseEntity.noContent().build();
    }

}
