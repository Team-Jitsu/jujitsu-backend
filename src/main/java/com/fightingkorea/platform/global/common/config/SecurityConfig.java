package com.fightingkorea.platform.global.common.config;

import com.fightingkorea.platform.global.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * CSRF는 서버가 브라우저의 세션/쿠키를 신뢰할 때 공격 위험이 생김.
     * JWT는 Authorization 헤더에 직접 담기 때문에 쿠키 자동 전송과 무관 → CSRF 공격 불가능.
     * REST API + JWT 조합은 주로 비동기 호출(fetch, axios) 사용.
     * 브라우저 폼 기반 요청이 아니므로 CSRF 보호 대상 아님.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/auth/**",
                                "/api/users/register",
                                "/api/trainers/register"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/specialties").permitAll()          // 전문분야 목록
                        .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()           // 카테고리 목록
                        .requestMatchers(HttpMethod.GET, "/api/videos").permitAll()               // 비디오 목록 조회
                        .requestMatchers(HttpMethod.GET, "/api/videos/*").permitAll()             // 비디오 상세 조회
                        .requestMatchers(HttpMethod.GET, "/api/trainers").permitAll()             // 트레이너 목록 조회
                        .requestMatchers(HttpMethod.GET, "/api/trainers/*").permitAll()           // 트레이너 상세 조회
                        .requestMatchers(HttpMethod.GET, "/api/search").permitAll()               // 강의/트레이너 검색
                        .requestMatchers(HttpMethod.GET, "/api/categories/*/videos").permitAll()  // 특정 카테고리의 비디오 목록
                        .requestMatchers(HttpMethod.GET, "/api/trainers/*/videos").permitAll()    // 특정 트레이너의 비디오 목록

                        // ADMIN 전용 API
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 나머지 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
