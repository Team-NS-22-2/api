package com.mju.insuranceCompany.application.viewlogic.service;

import com.mju.insuranceCompany.application.login.User;
import com.mju.insuranceCompany.application.repository.UserRepository;
import com.mju.insuranceCompany.application.viewlogic.dto.user.request.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean signUp(int cId, UserSignUpRequest request) {
        User user = User.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .roleId(cId)
                .build();
        user = userRepository.save(user);
        return user.getId() > 0;
    }
}
