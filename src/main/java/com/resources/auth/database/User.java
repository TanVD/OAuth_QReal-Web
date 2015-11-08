package com.resources.auth.database;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tanvd on 07.11.15.
 */
public class User implements Serializable, UserDetails {
    static Integer idInc = 0;

    private Integer id = 0;

    private String username;

    private String password;

    private Collection<GrantedAuthority> authority;

    public User(){
        idInc += 1;
    }

    public User(String username, String password, Collection<GrantedAuthority> authority) {
        idInc += 1;
        this.id = idInc;
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public Collection<GrantedAuthority> getAuthorities(){
        return authority;
    }

    public void setAuthorities(Collection<GrantedAuthority> authority) {
        this.authority = authority;
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
}




