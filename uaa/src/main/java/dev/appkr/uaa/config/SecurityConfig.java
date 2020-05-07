//package dev.appkr.uaa.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//
//@Configuration
//public class SecurityConfig extends ResourceServerConfigurerAdapter {
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//            .csrf()
//            .disable()
//            .headers()
//            .frameOptions()
//            .disable()
//        .and()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and()
//            .authorizeRequests()
//            .antMatchers("/actuator/**").permitAll();
//    }
//}
