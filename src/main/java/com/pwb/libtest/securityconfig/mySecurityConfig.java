package com.pwb.libtest.securityconfig;

import com.pwb.libtest.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class mySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService myDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().ignoringAntMatchers("/druid/*")
                .and()
                .authorizeRequests()

                .antMatchers("/api", "/api/*", "/api/admin/*").permitAll()

                .antMatchers("/webjars/**", "/static/**").permitAll()
                .antMatchers("/toregiste", "/doregiste", "/register").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/index").hasRole("vip0")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/index")
                .successHandler(new MyLoginSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .rememberMe();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(myDetailService)
                .passwordEncoder(new NonePasswordEncoder());
    }
}

    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN", "vip0")
                .and()
                .passwordEncoder(new NonePasswordEncoder());
    }*/
