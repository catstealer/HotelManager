package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/rooms").permitAll()
                .antMatchers(HttpMethod.GET,"/rooms/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/{id}/reservations").permitAll()
                .antMatchers(HttpMethod.GET,"/roomType").permitAll()
                .antMatchers(HttpMethod.GET,"/roomType/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/tag/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/tag").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/bills/statistics").hasRole("ADMIN")
                .antMatchers("/**").authenticated()
                .anyRequest().fullyAuthenticated()
                .and()
                .httpBasic();
    }

}


