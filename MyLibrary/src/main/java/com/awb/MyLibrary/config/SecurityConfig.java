package com.awb.MyLibrary.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {


    //Configurare filtru de securitate
    //Bean gestionat si injectat de catre  Spring Container
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //Folosim metoda "securityFilterChain" pentru
        //configurare filtre de securitate. Parametru "HttpSecurity" folosit pentru configurare reguli de securitate HTTP
        return http
                .authorizeHttpRequests( auth -> auth //metoda folosita pentru configurare reguli de autorizare la cererile HTTP
                        .requestMatchers("/").permitAll() //acces liber la "/" root
                        .requestMatchers("/register").permitAll() //same
                        .requestMatchers("/login").permitAll() //same
                        .requestMatchers("/logout").permitAll() //same
                        .requestMatchers("/book/**").authenticated() //solicitare autentificare pentru orice cerere la "/book/"
                        .anyRequest().authenticated() //cerere de autentificare pentru orice cerere care nu se afla in regulile de mai sus
                )
                //configurare autentificare prin formular si setare url de redirectionare la root "/" dupa log in.
                .formLogin(form -> form
                        .defaultSuccessUrl("/", true)
                )
                //configurare functionalitate, setare si redirectionare catre "/" root
                .logout(config -> config.logoutSuccessUrl("/"))
                .build();
    }


    //Criptarea parolelor folosind algoritmul BCrypt
    //passwordEncoder() - metoda de config Spring returnare obiect "PasswordEncoder"
    //Injectare PasswordEncoder pentru stocare parole criptate
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //BCryptPasswordEncoder este implementare a interfetei PasswordEncoder pentru a heshui parolele
    }
}


