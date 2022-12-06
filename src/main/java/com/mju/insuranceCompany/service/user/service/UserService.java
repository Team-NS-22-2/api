package com.mju.insuranceCompany.service.user.service;

import com.mju.insuranceCompany.service.user.controller.dto.UserBasicRequest;
import com.mju.insuranceCompany.service.user.domain.UserType;
import com.mju.insuranceCompany.service.user.domain.Users;
import com.mju.insuranceCompany.service.user.exception.DuplicateUserIdException;
import com.mju.insuranceCompany.service.user.exception.UserIdNotFoundException;
import com.mju.insuranceCompany.service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j @Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public void signUp(int cId, UserBasicRequest request) {
        checkDuplicateUserId(request.getUserId());

        String encodePassword = encoder.encode(request.getPassword());
        Users user = Users.builder()
                .userId(request.getUserId())
                .password(encodePassword)
                .roleId(cId)
                .type(UserType.ROLE_CUSTOMER)
                .build();
        userRepository.save(user);
    }

    public void signUpEmployee(int eId, UserBasicRequest request, UserType type) {
        checkDuplicateUserId(request.getUserId());

        String encodePassword = encoder.encode(request.getPassword());
        Users user = Users.builder()
                .userId(request.getUserId())
                .password(encodePassword)
                .roleId(eId)
                .type(type)
                .build();
        userRepository.save(user);
    }

    /**
     * User ID 중복체크 메소드.
     * 만일 존재하는 ID라면, DuplicateUserIdException을 발생.
     * @param userId 회원가입을 요청한 user id
     */
    private void checkDuplicateUserId(String userId) {
        if(userRepository.findByUserId(userId).isPresent()) {
            throw new DuplicateUserIdException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findByUserId(id).orElseThrow(UserIdNotFoundException::new);
    }
}
