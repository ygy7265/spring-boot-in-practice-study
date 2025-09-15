package org.example.springbootinpractice.ch01;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;
@ToString
//@RequiredArgsConstructor
@ConfigurationProperties("app.sbip.ct")
public class CustomProperties {

    public CustomProperties(String name, String ip, @DefaultValue("9000") int port, Security security) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.security = security;
    }

    private final String name;
    private final String ip;
    private final int port;
    private final Security security;

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public Security getSecurity() {
        return security;
    }
    @AllArgsConstructor
    public static class Security {
        private final String token;
        private boolean enabled;
        private List<String> roles;


        public List<String> getRoles() {
            return roles;
        }

        public String getToken() {
            return token;
        }

        public boolean isEnabled() {
            return enabled;
        }

        @Override
        public String toString() {
            return "Security{" +
                    "enabled=" + enabled +
                    ", token='" + token + '\'' +
                    ", roles=" + roles +
                    '}';
        }
    }
}
