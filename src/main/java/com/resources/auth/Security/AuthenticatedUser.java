package com.resources.auth.Security;

import com.resources.auth.Database.Users.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by tanvd on 15.12.15.
 */
public class AuthenticatedUser {
    public static String getAuthenticatedUserName()
    {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticatedUser.getUsername();
    }

    public static String getAuthenticatedUserAuthority() {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticatedUser.getAuthority();
    }
}
