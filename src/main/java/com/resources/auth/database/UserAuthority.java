package com.resources.auth.database;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by tanvd on 08.11.15.
 */
public class UserAuthority implements GrantedAuthority{
    private String authority;

    public UserAuthority(String authority){
        this.authority = authority;
    }

    public String getAuthority(){
        return authority;
    }

    public void setAuthority(String authority){
        this.authority = authority;
    }
}
