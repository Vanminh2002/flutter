package org.example.apiflutter.controller;


import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.apiflutter.dto.request.AuthenticationRequest;
import org.example.apiflutter.dto.request.VerifyTokenRequest;
import org.example.apiflutter.dto.response.ApiResponse;
import org.example.apiflutter.dto.response.AuthenticationResponse;
import org.example.apiflutter.dto.response.VerifyTokenResponse;
import org.example.apiflutter.services.AuthenticationServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationServices authenticationServices;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        var result = authenticationServices.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }


    @PostMapping("/verify")
    ApiResponse<VerifyTokenResponse> login(@RequestBody VerifyTokenRequest verifyTokenRequest)
            throws ParseException, JOSEException {
        var result = authenticationServices.verify(verifyTokenRequest);
        return ApiResponse.<VerifyTokenResponse>builder()
                .result(result)
                .build();
    }
}
