package com.project.HR.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;  
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;  

@Configuration  
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {  

    @Override  
    public void configure(HttpSecurity http) throws Exception {  
        http  
        .authorizeRequests()
//        .antMatchers("/admin/**").hasAuthority("ADMIN")
//        .antMatchers("/user/**").hasRole("USER")
        .antMatchers( "/public/**").permitAll()  //making the public directory on the classpath root available without authentication
        .anyRequest().authenticated()  
            .and()  
        .formLogin()  
	        .usernameParameter("username")
	        .passwordParameter("password")
            .loginPage("/login")  //setting a login page 
            .defaultSuccessUrl("/role", true)
            //.failureUrl("/login-error")  //setting a login failure page 
            .permitAll();  //making sure that these are publicly available
        					//This is great for things like images, JavaScript files, and CSS files.
           // .and().csrf().disable() ;
    }  

    @Override  
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  
    	// creating a user with username user and password pass. The user has the USER role assigned to it.
//        auth.inMemoryAuthentication()  
//            .withUser("user")  
//            .password("{noop}pass") // Spring Security 5 requires specifying the password storage format  
//            .roles("USER");  
        auth.authenticationProvider(authenticationProvider());
    }  
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    

}
