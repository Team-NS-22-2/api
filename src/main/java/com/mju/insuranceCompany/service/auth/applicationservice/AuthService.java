package com.mju.insuranceCompany.service.auth.applicationservice;

import com.mju.insuranceCompany.service.auth.controller.dto.AuthBasicRequest;
import com.mju.insuranceCompany.service.auth.domain.AuthType;
import com.mju.insuranceCompany.service.auth.domain.Auth;
import com.mju.insuranceCompany.service.auth.exception.DuplicateUserIdException;
import com.mju.insuranceCompany.service.auth.exception.UserIdNotFoundException;
import com.mju.insuranceCompany.service.auth.repository.AuthRepository;
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
public class AuthService implements UserDetailsService {

    private final AuthRepository userRepository;
    private final PasswordEncoder encoder;

    public void signUp(int cId, AuthBasicRequest request) {
        checkDuplicateUserId(request.getUserId());

        String encodePassword = encoder.encode(request.getPassword());
        Auth auth = Auth.builder()
                .userId(request.getUserId())
                .password(encodePassword)
                .roleId(cId)
                .type(AuthType.ROLE_CUSTOMER)
                .build();
        userRepository.save(auth);
    }

    public void signUpEmployee(int eId, AuthBasicRequest request, AuthType type) {
        checkDuplicateUserId(request.getUserId());

        String encodePassword = encoder.encode(request.getPassword());
        Auth auth = Auth.builder()
                .userId(request.getUserId())
                .password(encodePassword)
                .roleId(eId)
                .type(type)
                .build();
        userRepository.save(auth);
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
