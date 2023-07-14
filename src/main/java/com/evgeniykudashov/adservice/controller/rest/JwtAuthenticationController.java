package com.evgeniykudashov.adservice.controller.rest;


import com.evgeniykudashov.adservice.dto.request.UsernamePasswordRequestDto;
import com.evgeniykudashov.adservice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "provides API about authentication")
@SecuritySchemes({
        @SecurityScheme(type = SecuritySchemeType.HTTP,
                name = "jwt authentication",
                scheme = "bearer",
                bearerFormat = "JWT",
                in = SecuritySchemeIn.COOKIE
        )
})
@RestController
@RequestMapping(value = "/api/v1/authentications",

        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor(onConstructor_ = @Autowired)
public class JwtAuthenticationController {

    public static final String ACCESS_TOKEN = "access_token";
    protected AuthenticationService authenticationService;

    @PostMapping("/jwt")
    public ResponseEntity<Void> createJwtAuthentication(@RequestBody @Valid UsernamePasswordRequestDto dto) {
        return ResponseEntity.noContent()
                .headers(headers -> headers.add(HttpHeaders.SET_COOKIE,
                        ResponseCookie.from(ACCESS_TOKEN, authenticationService.generateJwtToken(dto))
                                .httpOnly(true)
                                .secure(true)
                                .build()
                                .toString()))
                .build();
    }
}
