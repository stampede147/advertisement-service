package com.evgeniykudashov.adservice.service.impl;

import com.evgeniykudashov.adservice.dto.request.UsernamePasswordRequestDto;
import com.evgeniykudashov.adservice.exception.servicelayer.NotFoundEntityException;
import com.evgeniykudashov.adservice.exception.servicelayer.PasswordMismatchException;
import com.evgeniykudashov.adservice.model.user.User;
import com.evgeniykudashov.adservice.repository.UserRepository;
import com.evgeniykudashov.adservice.security.jwt.tokenfactory.JwtTokenFactory;
import com.evgeniykudashov.adservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenFactory factory;

    @Transactional(readOnly = true)
    public String generateJwtToken(UsernamePasswordRequestDto dto) {
        User user = findUserByUsername(dto.getUsername());

        validatePassword(dto.getPassword(), user.getPassword());

        return factory.createToken(user.getUsername(), Collections.singleton(user.getRole()));
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(NotFoundEntityException::new);
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new PasswordMismatchException("provided password is wrong");
        }
    }

}
