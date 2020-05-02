package io.vrooms.oauth;

import java.util.Map;

public class OAuthUserInfo {

	public static final String EMAIL = "email";
	public static final String NAME = "name";

	private Map<String, Object> attributes;

	public OAuthUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getName() {
		return (String) attributes.get(NAME);
	}

	public String getEmail() {
		return (String) attributes.get(EMAIL);
	}
}
