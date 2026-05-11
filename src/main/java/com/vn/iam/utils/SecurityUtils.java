package com.vn.iam.utils;

import com.vn.lib.redis.UserRedisDto;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static UserRedisDto getCurrentUser() {
        return (UserRedisDto) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public static String getAccessToken() {
        return (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getCredentials();
    }
}
