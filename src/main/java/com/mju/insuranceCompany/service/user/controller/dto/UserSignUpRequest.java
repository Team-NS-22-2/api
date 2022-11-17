package com.mju.insuranceCompany.service.user.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignUpRequest {
    private String userId;
    private String password;
}
