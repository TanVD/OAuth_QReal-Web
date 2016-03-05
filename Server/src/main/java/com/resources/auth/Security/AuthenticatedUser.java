package com.resources.auth.Security;

import com.resources.auth.Database.Users.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * This class represent authentication object of user.
 * It can return name of authenticated user, which interacts with the service or
 * his UserAuthorities.
 * @author TanVD
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
