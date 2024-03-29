package ru.drdrapp.drappogram.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.drdrapp.drappogram.tmp.TmpClass;

@Configuration
@EnableJpaRepositories("ru.drdrapp.drappogram.repositories")
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "MyTmpBean")
    TmpClass tmpClass() {
        var tmpC = new TmpClass();
        tmpC.setTmpData("Init string data");
        return tmpC;
    }
}