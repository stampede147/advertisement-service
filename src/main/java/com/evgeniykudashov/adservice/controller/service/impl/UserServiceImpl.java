package com.evgeniykudashov.adservice.controller.service.impl;

import com.evgeniykudashov.adservice.controller.exception.NotFoundUserException;
import com.evgeniykudashov.adservice.controller.repository.UserRepository;
import com.evgeniykudashov.adservice.controller.service.UserService;
import com.evgeniykudashov.adservice.controller.service.helper.ReflectionUtility;
import com.evgeniykudashov.adservice.controller.service.helper.StringUtils;
import com.evgeniykudashov.adservice.model.domain.DomainLayerConstants;
import com.evgeniykudashov.adservice.model.domain.aggregate.user.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@AllArgsConstructor(onConstructor_ = @Autowired)
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Transactional
    @Override
    public long create(User user) {
        return userRepository.save(user).getId();
    }

    @Transactional
    @Override
    public void patchUpdate(Map<String, Object> data, Long userId) {
        data.forEach((k, v) -> ReflectionUtility.callMethod(this.findById(userId),
                DomainLayerConstants.UPDATE_METHOD_PREFIX.concat(StringUtils.capitalize(k)),
                v));

    }

    @Transactional
    @Override
    public void remove(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(long userId) {
        return userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
    }
}
