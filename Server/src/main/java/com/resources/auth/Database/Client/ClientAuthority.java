package com.resources.auth.Database.Client;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by tanvd on 02.04.16.
 */
//FIXME Hardcoded authorites
public class ClientAuthority implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "ROLE_CLIENT";
    }
}
