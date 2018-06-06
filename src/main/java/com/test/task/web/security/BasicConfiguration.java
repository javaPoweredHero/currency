package com.test.task.web.security;

import com.test.task.common.systemDictionaries.roles.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
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
                .authorities(UserRoles.USER).build());
        manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("admin"))
                .authorities(UserRoles.USER, UserRoles.ADMIN).build());
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
                .authenticated().antMatchers("/api/v1/**").hasAuthority("ADMIN")
                .and()
                .httpBasic()
                .authenticationEntryPoint(basicEntryPoint);
    }
}
