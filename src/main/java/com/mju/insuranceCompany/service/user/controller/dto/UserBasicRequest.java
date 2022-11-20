package com.mju.insuranceCompany.service.user.controller.dto;

import com.mju.insuranceCompany.service.user.domain.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicRequest {
    private String userId;
    private String password;
}
