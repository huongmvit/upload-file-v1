package com.upload.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vn.lib.constant.http.CMHttpHeaders;
import com.vn.lib.enu.user.UserRedis;
import com.vn.lib.redis.UserRedisDto;
import com.vn.lib.redis.service.CMRedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AppFilter extends OncePerRequestFilter {

    private final CMRedisService redisService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {

            String header = request.getHeader(CMHttpHeaders.AUTHORIZATION);

            // Không có token -> cho đi tiếp
            if (header == null || !header.startsWith(CMHttpHeaders.PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }

            String accessToken =
                    UserRedis.USER +
                            header.substring(CMHttpHeaders.PREFIX.length()).trim();

            String redisJson = redisService.getFromKey(accessToken);

            if (redisJson != null) {

                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                UserRedisDto dto =
                        mapper.readValue(redisJson, UserRedisDto.class);

                List<SimpleGrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority("SUPER_ADMIN")
                );

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                dto,
                                accessToken,
                                authorities
                        );

                auth.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(auth);
            }

            // 🔥 Quan trọng
            filterChain.doFilter(request, response);

        } catch (Exception e) {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        return path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/webjars");
    }
}