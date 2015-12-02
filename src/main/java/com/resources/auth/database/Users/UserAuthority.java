package com.resources.auth.database.Users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tanvd on 08.11.15.
 */
public class UserAuthority implements GrantedAuthority{
    private static int idInc = 0;

    private int id = 0;

    private String authority;

    private User user;

    public UserAuthority(String authority){
        this.authority = authority;
        id = idInc;
        idInc += 1;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
