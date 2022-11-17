package com.mju.insuranceCompany.service.user.service;

import com.mju.insuranceCompany.service.user.domain.Users;
import com.mju.insuranceCompany.service.user.domain.UserType;
import com.mju.insuranceCompany.service.user.repository.UserRepository;
import com.mju.insuranceCompany.service.user.controller.dto.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public boolean signUp(int cId, UserSignUpRequest request) {
        Users user = Users.builder()
                .userId(request.getUserId())
                .password(request.getPassword())
                .roleId(cId)
                .type(UserType.CUSTOMER)
                .build();
        user = userRepository.save(user);
        return user.getId() > 0;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Users user = userRepository.findByUserId(id).orElseThrow();
        return new org.springframework.security.core.userdetails.
                User(user.getUserId(), user.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(user.getType().name())));
    }
}
