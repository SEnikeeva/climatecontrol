package ru.itis.security.defails;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.itis.model.State;
import ru.itis.model.User;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
public class UserDetailsImpl implements org.springframework.security.core.userdetails.UserDetails {
    private Integer userId;
    private String role;
    private String name;
    private User user;

    public User getUser() { return user; }

    public Integer getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { return user.getState().equals(State.CONFIRMED);}
}
