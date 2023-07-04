package com.evgeniykudashov.adservice.controller.rest;


import com.evgeniykudashov.adservice.mapper.dto.response.PageDto;
import com.evgeniykudashov.adservice.service.AdvertisementService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/advertisements")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class UserAdvertisementController {

    private AdvertisementService advertisementService;

    @GetMapping()
    public ResponseEntity<PageDto<?>> onGetAll(@PathVariable Long userId,
                                               @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(advertisementService.findAllByUserId(userId, pageable));
    }
}
