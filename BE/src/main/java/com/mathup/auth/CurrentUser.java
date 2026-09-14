package com.mathup.auth;

import jakarta.servlet.http.HttpServletRequest;

public final class CurrentUser {

    private CurrentUser() {
    }

    public static Long requireUserId(HttpServletRequest request) {
        Object userId = request.getAttribute(JwtAuthFilter.USER_ID_ATTRIBUTE);
        if (userId == null) {
            throw new UnauthorizedException();
        }
        return (Long) userId;
    }
}
