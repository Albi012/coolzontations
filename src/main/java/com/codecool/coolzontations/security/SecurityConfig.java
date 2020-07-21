package com.codecool.coolzontations.security;

import com.codecool.coolzontations.model.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.codecool.coolzontations.model.Roles.ADMIN;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private final JwtTokenService jwtTokenServices;

    public SecurityConfig(JwtTokenService jwtTokenServices) {
        this.jwtTokenServices = jwtTokenServices;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(new JwtTokenFilter(jwtTokenServices), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "/auth/*" , "/registration").permitAll()
                .antMatchers("/admin/**").hasRole(ADMIN.name())
                .anyRequest()
                    .authenticated();
    }

}
