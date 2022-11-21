package com.mju.insuranceCompany.service.user.service;

import com.mju.insuranceCompany.global.exception.user.UserIdNotFoundException;
import com.mju.insuranceCompany.service.user.controller.dto.UserBasicRequest;
import com.mju.insuranceCompany.service.user.domain.UserType;
import com.mju.insuranceCompany.service.user.domain.Users;
import com.mju.insuranceCompany.service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public boolean signUp(int cId, UserBasicRequest request) {
        String encodePassword = encoder.encode(request.getPassword());
        Users user = Users.builder()
                .userId(request.getUserId())
                .password(encodePassword)
                .roleId(cId)
                .type(UserType.ROLE_CUSTOMER)
                .build();
        user = userRepository.save(user);
        return user.getId() > 0;
    }

    public void signUpEmployee(int eId, UserBasicRequest request, UserType type) {
        String encodePassword = encoder.encode(request.getPassword());
        Users user = Users.builder()
                .userId(request.getUserId())
                .password(encodePassword)
                .roleId(eId)
                .type(type)
                .build();
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findByUserId(id).orElseThrow(UserIdNotFoundException::new);
//        Users user = userRepository.findByUserId(id).orElseThrow();
//        return new org.springframework.security.core.userdetails.
//                User(user.getUserId(), user.getPassword(),
//                List.of(new SimpleGrantedAuthority(user.getType().name())));
    }
}
