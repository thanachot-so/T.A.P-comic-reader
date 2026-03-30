package com.tapcomiccomicreader.helperclass;

import com.tapcomiccomicreader.config.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public static Integer getCurrentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        var userDetail = auth.getPrincipal();

        if (userDetail instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.getUserId();
        }

        return null;
    }
}
