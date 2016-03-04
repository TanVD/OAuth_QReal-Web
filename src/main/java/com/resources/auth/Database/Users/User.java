package com.resources.auth.Database.Users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is a class of user.
 * This class also used as entity in database.
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable, UserDetails {
    static Integer idInc = 0;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id = 0;

    @Column(name = "LOGIN")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    /**
     * Because of some problems with List<Authorities> (in moment of saving to database)
     * we save authorities serialized to string.
     */
    @Column(name = "AUTHORITIES")
    private String authority;

    /**
     * Converts serialized in string authorities into set
     */
    private Set<GrantedAuthority> convertStringToSet(String authority)
    {
        Set<GrantedAuthority> userRoles = new HashSet<GrantedAuthority>();
        for (String str : authority.split("\\s+")) {
            userRoles.add(new UserAuthority(str));
        }
        return userRoles;
    }

    /**
     * Serialize set of authorities into string
     */
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
        idInc++;
    }

    public User(String username, String password, Collection<GrantedAuthority> authority) {
        idInc += 1;
        this.id = idInc;
        this.username = username;
        this.password = password;
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




