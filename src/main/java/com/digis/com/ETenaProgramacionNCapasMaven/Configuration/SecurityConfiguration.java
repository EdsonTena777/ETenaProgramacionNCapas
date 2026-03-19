
package com.digis.com.ETenaProgramacionNCapasMaven.Configuration;

import com.digis.com.ETenaProgramacionNCapasMaven.Service.UserDetailJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Autowired
    private UserDetailJPA userDetailJPA;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer
            .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
            .requestMatchers("/error").permitAll()
            .requestMatchers("/usuario/**").hasAnyRole("Admin", "Empleado", "Invitado", "Usuario")
            .anyRequest().authenticated()
        )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login") 
                .defaultSuccessUrl("/usuario/getall", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .deleteCookies("JSESSIONID") 
                .invalidateHttpSession(true) 
                .permitAll()
            )
            .userDetailsService(userDetailJPA);
        return http.build();
    }

    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

