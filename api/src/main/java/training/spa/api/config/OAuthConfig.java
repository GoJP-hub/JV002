package training.spa.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "oauth2")
@Data
public class OAuthConfig {
	private String clientId;

}
