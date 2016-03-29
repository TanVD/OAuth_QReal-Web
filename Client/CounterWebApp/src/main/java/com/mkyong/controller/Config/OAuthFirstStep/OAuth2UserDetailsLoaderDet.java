package com.mkyong.controller.Config.OAuthFirstStep;

import com.racquettrack.security.oauth.OAuth2UserDetailsLoader;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by tanvd on 29.03.16.
 */
public class OAuth2UserDetailsLoaderDet implements OAuth2UserDetailsLoader {
    //fastly mocked
    public UserDetails getUserByUserId(Object id) {
        Set<GrantedAuthority> set = new HashSet<GrantedAuthority>();
        GrantedAuthority auth = new SimpleGrantedAuthority("USER");
        set.add(auth);
        return new User("marissa", "wombat", set);
    }

    public UserDetails updateUser(UserDetails userDetails, Map userInfo) {
        return null;
    }

    public UserDetails createUser(Object id, Map userInfo) {
        return null;
    }

    public boolean isCreatable(Map userInfo) {
        return false;
    }
}
