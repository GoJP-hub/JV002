package training.spa.api.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import training.spa.api.config.OAuthConfig;
import training.spa.api.exception.ApplicationErrorException;

@Service
public class AuthService {
	protected final static Logger logger = LoggerFactory.getLogger(AuthService.class);

	static final JsonFactory JSON_FACTORY = new JacksonFactory();
	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	private final static ApplicationErrorException appException = new ApplicationErrorException("A001", "authorization",
			"Unauthorized access.");

	@Autowired
	OAuthConfig oAuthConfig;

	public Map<String, String> getUserAttr(String authorization)
			throws ApplicationErrorException, IOException, GeneralSecurityException {
		String[] tmp = authorization.split(" ");
		String jwt = tmp[1];
		logger.info("JWT: " + jwt);

		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
				.setAudience(Arrays.asList(oAuthConfig.getClientId()))
				.setIssuer("accounts.google.com")
				.build();

		GoogleIdToken idToken = verifier.verify(jwt);

		if (idToken != null) {
			GoogleIdToken.Payload payload = idToken.getPayload();

			Map<String, String> userAttr = convClaimToMap(payload);
			logger.info(userAttr.toString());
			return userAttr;
		} else {
			logger.error(appException.toString() + "Unauthorized access.");
			throw appException;
		}
	}

	private Map<String, String> convClaimToMap(GoogleIdToken.Payload payload) {
		Map<String, String> attributes = new HashMap<>();

		attributes.put("userId", payload.getSubject());
		attributes.put("email", payload.getEmail());
		attributes.put("emailVerified", payload.getEmailVerified().toString());
		attributes.put("name", (String) payload.get("name"));
		attributes.put("pictureUrl", (String) payload.get("picture"));
		attributes.put("locale", (String) payload.get("locale"));
		attributes.put("familyName", (String) payload.get("family_name"));
		attributes.put("givenName", (String) payload.get("given_name"));

		return attributes;
	}

}
