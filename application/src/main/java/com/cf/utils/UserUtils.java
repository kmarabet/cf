package com.cf.utils;

import com.cf.service.security.UserDetailsServiceImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by aliaksandr.krasitski on 9/17/2014.
 */
public class UserUtils {
    private UserUtils(){}

    public static UserDetails getCurrentUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isCurrentUserAdmin() {
            return getCurrentUser().getAuthorities().contains(new SimpleGrantedAuthority(UserDetailsServiceImpl.ROLE_ADMIN));
    }
}
