package com.resources.auth.database.Users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.awt.font.GraphicAttribute;
import java.io.Serializable;
import java.util.*;

/**
 * Created by tanvd on 07.11.15.
 */
public class User implements Serializable, UserDetails {
    static Integer idInc = 0;

    private Integer id = 0;

    private String username;

    private String password;

    private String authority;

    private Set<GrantedAuthority> convertStringToSet(String authority)
    {
        Set<GrantedAuthority> userRoles = new HashSet<GrantedAuthority>();
        for (String str : authority.split("\\s+")) {
            userRoles.add(new UserAuthority(str));
        }
        return userRoles;
    }

    private String convertSetToString(Collection<GrantedAuthority> userAuthorities)
    {
        String userRoles = new String();
        for (GrantedAuthority auth : userAuthorities)
        {
            userRoles += auth.getAuthority() + " ";
        }
        return userRoles;
    }

    public User(){
        idInc += 1;
    }

    public User(String username, String password, Collection<GrantedAuthority> authority) {
        idInc += 1;
        this.id = idInc;
        this.username = username;
        this.password = password;
        this.authority = convertSetToString(authority);
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {

        this.authority = authority;
    }

    public Collection<GrantedAuthority> getAuthorities(){
        return convertStringToSet(authority);
    }

    public void setAuthorities(Collection<GrantedAuthority> authority) {
        this.authority = convertSetToString(authority);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isAdmin() {
        return authority.contains("ROLE_ADMIN");
    }
}




