package com.resources.auth.Security.Utils;

import com.resources.auth.Database.Users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

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
        String auth = "";
        for (GrantedAuthority authority : authenticatedUser.getAuthorities()) {
            auth += authority.getAuthority() + " ";
        }
        return auth;
    }
}
