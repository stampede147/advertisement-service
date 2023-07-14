package com.evgeniykudashov.adservice.service.impl;

import com.evgeniykudashov.adservice.dto.request.UserRequestDto;
import com.evgeniykudashov.adservice.dto.response.UserResponseDto;
import com.evgeniykudashov.adservice.exception.servicelayer.InvalidIdException;
import com.evgeniykudashov.adservice.exception.servicelayer.NotFoundEntityException;
import com.evgeniykudashov.adservice.mapper.UserMapper;
import com.evgeniykudashov.adservice.model.user.Role;
import com.evgeniykudashov.adservice.repository.UserRepository;
import com.evgeniykudashov.adservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    @Transactional
    @Override
    public long create(UserRequestDto dto) {
        return userRepository.save(mapper.toUser(dto, encoder.encode(dto.getPassword()), Role.USER))
                .getId();
    }

    @Transactional
    @Override
    public void remove(long userId) {
        validateId(userId);

        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto findById(long userId) {
        validateId(userId);

        return mapper.toUserResponseDto(userRepository.findById(userId).orElseThrow(NotFoundEntityException::new));
    }

    private void validateId(long userId) {
        if (userId <= 0) {
            throw new InvalidIdException("provided id should be positive, id: " + userId);
        }
    }

}
