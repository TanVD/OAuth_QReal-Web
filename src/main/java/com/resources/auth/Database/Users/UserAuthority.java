package com.resources.auth.Database.Users;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by tanvd on 08.11.15.
 */
public class UserAuthority implements GrantedAuthority{
    private static int idInc = 0;

    private int id = 0;

    private String authority;

    public UserAuthority(String authority){
        this.authority = authority;
        id = idInc;
        idInc += 1;
    }

    public String getAuthority(){
        return authority;
    }

    public void setAuthority(String authority){
        this.authority = authority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
