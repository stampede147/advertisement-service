package com.evgeniykudashov.adservice.service;

import com.evgeniykudashov.adservice.mapper.dto.PageDto;
import com.evgeniykudashov.adservice.mapper.dto.request.CreateAdvertisementRequestDto;
import com.evgeniykudashov.adservice.mapper.dto.response.AdvertisementResponseDto;
import org.springframework.data.domain.Pageable;

public interface AdvertisementService {

    long create(CreateAdvertisementRequestDto advertisement);

    void remove(long advertisementId);

    PageDto<AdvertisementResponseDto> findAllByUserId(long userId, Pageable pageable);

    AdvertisementResponseDto findById(long advertisementId);


}
