package com.mju.insuranceCompany.application.viewlogic.dto.user.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignUpRequest {
    private String userId;
    private String password;
}
