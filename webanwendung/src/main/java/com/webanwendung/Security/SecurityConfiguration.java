package com.webanwendung.Security;

import com.webanwendung.Service.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
   @Autowired MyUserDetailsService userDetailsService;
   
   @Bean PasswordEncoder getpasswordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

   }   

   @Override 
   protected void configure(AuthenticationManagerBuilder authmanagerbuilder) throws Exception{
       PasswordEncoder pwenc = getpasswordEncoder();
       authmanagerbuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(pwenc);
        authmanagerbuilder.inMemoryAuthentication()
            .withUser("admin")
            .password(pwenc.encode("geheim"))
            .roles("USER","ADMIN");
   } 


  protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
           .antMatchers("/login,", "/logout","/registation","/h2-console","/h2-console/**", "/rest/**").permitAll()
           .antMatchers("/sighting","sighting/**").authenticated() 
           .antMatchers("/rest/**").hasRole("USER")
           .anyRequest().hasRole("ADMIN")
        .and()
           .formLogin()
           .loginPage("/login")
           .defaultSuccessUrl("/sighting")
           .permitAll()
        .and()   // Funktioniert noch nicht richtig
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")
            .permitAll();
  }  





} 



