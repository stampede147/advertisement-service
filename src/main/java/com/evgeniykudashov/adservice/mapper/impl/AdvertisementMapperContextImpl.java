package com.evgeniykudashov.adservice.mapper.impl;

import com.evgeniykudashov.adservice.mapper.AdvertisementMapper;
import com.evgeniykudashov.adservice.mapper.AdvertisementMapperContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.NoSuchElementException;


@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AdvertisementMapperContextImpl implements AdvertisementMapperContext {

    private final Map<AdvertisementMapperType, AdvertisementMapper> mappers;

    @Override
    public AdvertisementMapper getMapper(AdvertisementMapperType type) throws NoSuchElementException {
        return mappers.computeIfAbsent(type, (typ) -> {
            throw new NoSuchElementException("not found such mapper for key: " + type);
        });
    }

}
