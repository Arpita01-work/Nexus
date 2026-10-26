package com.arpita.nexus.security.userdetails;

import com.arpita.nexus.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(
            new SimpleGrantedAuthority(
                "ROLE_" + user.getRole().name()
            )
        );
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE.equals(
            user.getAccountNonExpired()
        );
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE.equals(
            user.getAccountNonLocked()
        );
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE.equals(
            user.getCredentialsNonExpired()
        );
    }

    @Override
    public boolean isEnabled() {

        return Boolean.TRUE.equals(
            user.getEnabled()
        )
            && Boolean.TRUE.equals(
            user.getActive()
        );
    }

    public User getUser() {
        return user;
    }
}
