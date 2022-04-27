package com.rojek.projectpp.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile(ProfileNames.USERS_IN_MEMORY)
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder) {

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        User.UserBuilder userBuilder = User.builder();

        UserDetails admin = userBuilder
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails user = userBuilder
                .username("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build();

        UserDetails superuser = userBuilder
                .username("superuser")
                .password(passwordEncoder.encode("superuser"))
                .roles("USER", "ADMIN")
                .build();

        manager.createUser(admin);
        manager.createUser(user);
        manager.createUser(superuser);

        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/bootstrap-5.1.3-dist/**", "/css/**",
                        "/", "/login", "/register/**", "/home").permitAll()
//                .antMatchers("/register**").permitAll()
//                .antMatchers("/info**").hasRole("USER")
//                .antMatchers("/deleteUser**", "/addUser**", "/editUser**").hasRole("ADMIN")
                .anyRequest()
                .authenticated();
        http
                .formLogin()//uwierzytelnienie poprzez formularz logowania
                .loginPage("/login")//formularz dostępny jest pod URL
                .defaultSuccessUrl("/home", true)
                .permitAll();//przyznaj dostęp wszystkim użytkownikom
        http.exceptionHandling()
                .accessDeniedPage("/error403");//nie jest to nazwa widoku tylko url!!!

        http.logout().logoutSuccessUrl("/").permitAll();

    }
}
