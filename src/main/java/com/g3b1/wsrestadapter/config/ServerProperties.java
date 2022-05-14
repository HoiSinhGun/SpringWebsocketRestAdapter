package com.g3b1.wsrestadapter.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 2022-05-14 09:33 - gun
 */
@Getter
@Setter
@Component
@ToString
@ConfigurationProperties(prefix = "server")
public class ServerProperties {
    public Integer port;
    public String appId;
}
