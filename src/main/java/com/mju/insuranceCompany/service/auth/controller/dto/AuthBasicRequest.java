package com.mju.insuranceCompany.service.auth.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthBasicRequest {
    private String userId;
    private String password;
}
