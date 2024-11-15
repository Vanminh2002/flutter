package org.example.apiflutter.configuration;

import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @NonFinal
    @Value("${jwt.signKey}")
    protected String signKey;


    public final String[] PUBLIC_ENDPOINTS = {
            "/user",
            "/user/{id}",
            "/auth/token",
            "/auth/verify",
            "/product",
            "/product/product-by-brand",
            "/product/by-category/{category}",
            "/images/image/download/{imageId}",
            "/images/{imageId}",
            "/images/by-productId/{productId}",

            "/images/upload",
            "/product/{id}",
            "/file",
            "/cart-items",
            "/cart-items/add",
            "/cart-items",
            "/cart",

            "/category",
            "/category/{id}",
            "/roles",
            "/permission",
            "/flutter"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers("/flutter/").permitAll().
                        requestMatchers(PUBLIC_ENDPOINTS).permitAll()


                        .anyRequest().authenticated());


        // authenticate provider
        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer ->
                                jwtConfigurer.decoder(jwtDecoder())
                                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

//        httpSecurity.csrf(csrf -> {
//            try {
//                csrf.disable().authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/user").permitAll().anyRequest().authenticated());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });


        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");


        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        // cần sign key và thuật toán dùng trong header của JWT
        SecretKeySpec secretKeySpec = new SecretKeySpec(signKey.getBytes(), "HS512");

        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }


//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setMaxUploadSize(5000000); // Cấu hình giới hạn kích thước file upload
//        return resolver;
//    }
}
