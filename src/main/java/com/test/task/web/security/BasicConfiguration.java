package com.test.task.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.test.task.common.domain.enums.UserRoles;

@Configuration
public class BasicConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    BasicEntryPoint basicEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password(passwordEncoder().encode("user"))
                .roles(UserRoles.USER.getName()).build());
        manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("admin"))
                .roles(UserRoles.USER.getName(), UserRoles.ADMIN.getName()).build());
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/v2/*", "/swagger/**", "/webjars/**", "/swagger-resources/**",
                        "/swagger-ui.html**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(basicEntryPoint);
    }
}
