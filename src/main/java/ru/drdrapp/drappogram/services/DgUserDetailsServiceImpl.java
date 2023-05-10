package ru.drdrapp.drappogram.services;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.drdrapp.drappogram.repositories.DgUserRepository;

@Service
public class DgUserDetailsServiceImpl implements UserDetailsService {

    final DgUserRepository dgUserRepository;

    public DgUserDetailsServiceImpl(DgUserRepository dgUserRepository) {
        this.dgUserRepository = dgUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return new DgUserDetailsImpl(dgUserRepository.findOneByLogin(login).orElseThrow(IllegalArgumentException::new));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}