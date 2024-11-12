package org.example.apiflutter.services;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.example.apiflutter.dto.request.AuthenticationRequest;
import org.example.apiflutter.dto.request.VerifyTokenRequest;
import org.example.apiflutter.dto.response.AuthenticationResponse;
import org.example.apiflutter.dto.response.VerifyTokenResponse;
import org.example.apiflutter.entity.User;
import org.example.apiflutter.exception.AppException;
import org.example.apiflutter.exception.ErrorCode;
import org.example.apiflutter.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServices {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationServices.class);
    UserRepository userRepository;
    @NonFinal
    @Value("${jwt.signKey}")
    protected String SignKey;

    public VerifyTokenResponse verify(VerifyTokenRequest verifyTokenRequest)
            throws JOSEException, ParseException {
        var token = verifyTokenRequest.getToken();

        JWSVerifier verifier = new MACVerifier(SignKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);
        // lấy ra thời hạn của token
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        // thời gian hết hạn phải sau thời điểm hiện tại
        return VerifyTokenResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();


    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        return passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();

    }

    String generateToken(User user) {
        // Header gồm thuật toán cần dùng
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        // body gồm nội dung ta gửi trong token
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                // đại diện cho user đăng nhập
                .subject(user.getUsername())
                // xác định token được issuer từ ai
                .issuer("space")
                // thời gian
                .issueTime(new Date())
                // xác định thời hạn
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(user))
                .build();
        // gồm claim set dùng dưới dạng json
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());


        // gồm header và payload
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);


        // khóa để kí và khóa giải mã trùng nhau
        try {
            jwsObject.sign(new MACSigner(SignKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can't create token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());

                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));

            });
        return stringJoiner.toString();
    }

    ;

}
