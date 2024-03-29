package ru.drdrapp.drappogram.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.models.State;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public record DgUserDetails(DgUser dgUser) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return dgUser.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return dgUser.getPassword();
    }

    @Override
    public String getUsername() {
        return dgUser.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !dgUser.getState().equals(State.BANNED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return dgUser.getState().equals(State.ACTIVE);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DgUserDetails) obj;
        return Objects.equals(this.dgUser, that.dgUser);
    }

    @Override
    public String toString() {
        return "Custom user["
                + dgUser.getLogin() + "/ "
                + dgUser.getLastName() + "/ "
                + dgUser.getFirstName() + "/ "
                + dgUser.getEmail() + ']';
    }

    public DgUser getDgUser(){
        return dgUser;
    }

}