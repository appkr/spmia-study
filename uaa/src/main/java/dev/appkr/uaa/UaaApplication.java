package dev.appkr.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@SpringBootApplication
@EnableResourceServer
@EnableAuthorizationServer
public class UaaApplication {

    @GetMapping(path = "/users", produces = "application/json")
    public Map<String, Object> getUser(OAuth2Authentication user) {
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", user.getUserAuthentication().getPrincipal());
        userInfo.put("authorities", user.getUserAuthentication().getAuthorities());

        return userInfo;
    }

    public static void main(String[] args) {
        SpringApplication.run(UaaApplication.class, args);
    }

}
