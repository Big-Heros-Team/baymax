package com.example.bayMax.Security;

import com.example.bayMax.Infrastructure.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .authorizeRequests()
                .antMatchers("/", "/signup", "/login", "/*.css", "/*.PNG", "/H2-console/**", "/addDrug", "/UserDrugs", "https://dailymed.nlm.nih.gov/dailymed/services/v2/*", "/reviews").permitAll()
                .antMatchers("/", "/about", "/allBlogs", "/signup", "/login", "/*.css", "/*.PNG", "/*.png", "/*.js", "/*.svg", "/*.ttf", "/resources/**", "/fonts/**", "/css/**", "/contactform/**", "/img/**", "/js/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/*.css").permitAll()
                .antMatchers("/*.js").permitAll()
                .antMatchers("/*.PNG").permitAll()
                .antMatchers("/*.jpg").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/doctors", "/delete").hasAuthority("ADMIN")
                .antMatchers("/patients").hasAuthority("DOCTOR")
                .antMatchers("/addReviews", "/addReviewsForm", "/appointment").hasAuthority("USER")
                .anyRequest().authenticated()//any other pages you have to be authenticated
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/myprofile")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}


