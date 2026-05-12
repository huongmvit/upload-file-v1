package com.upload.config;

import com.upload.utils.SecurityUtils;
import com.vn.lib.redis.UserRedisDto;
import com.vn.lib.redis.service.CMRedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissionFilter extends OncePerRequestFilter {

    private final CMRedisService redisService;
    private static final AntPathMatcher matcher = new AntPathMatcher();

    private static final String[] AUTH_NO_CHECK = {
            "/api/user/login",
            "/api/user/get-pass/*",
            "/images/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    private static final String[] AUTH_MEMBER_ADMIN = {
            "/api/file/avartar/uploads",
            "/api/file/room/uploads",
            "/api/file/uploads"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (isWhiteList(path)) {
            chain.doFilter(request, response);
            return;
        }
        UserRedisDto user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            user = SecurityUtils.getCurrentUser();
        }
        List<String> guestPermissions = getNonPermissions();
        if (match(path, guestPermissions)) {
            chain.doFilter(request, response);
            return;
        }
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String roleKey = "SUPER_ADMIN";
        List<String> rolePermissions = getPermissions(roleKey);
        if (!match(path, rolePermissions)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(request, response);
    }

    /**
     * 🔥 Match path với list pattern
     */
    private boolean match(String path, List<String> patterns) {
        if (patterns == null || patterns.isEmpty()) return false;

        for (String pattern : patterns) {

            // normalize
            pattern = pattern.trim();
            path = path.trim();

            // match chuẩn
            if (matcher.match(pattern, path)) {
                return true;
            }

            // 🔥 FIX /** không match root
            if (pattern.endsWith("/**")) {
                String base = pattern.substring(0, pattern.length() - 3);

                if (path.equals(base) || path.startsWith(base + "/")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 🔥 Load permission từ Redis
     */
    private List<String> getPermissions(String key) {
        try {
            return new ArrayList<>(Arrays.asList(AUTH_MEMBER_ADMIN));
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * 🔥 Load permission từ Redis
     */
    private List<String> getNonPermissions() {
        try {
            return new ArrayList<>(Arrays.asList(AUTH_NO_CHECK));
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * 🔥 White list (không cần check permission)
     */
    private boolean isWhiteList(String path) {
        return path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/swagger-ui.html")
                || path.startsWith("/api/auth")
                || path.startsWith("/public");
    }
}