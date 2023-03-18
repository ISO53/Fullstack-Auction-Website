package com.iso.bidding.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties("cache")
@Getter
@Setter
public class CacheProperties {

    Map<String, Long> expirations = new HashMap<String, Long>();
}
