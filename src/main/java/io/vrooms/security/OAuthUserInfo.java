package io.vrooms.security;

import java.util.Map;

public class OAuthUserInfo {

	private Map<String, Object> attributes;

	public OAuthUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getId() {
		return (String) attributes.get("sub");
	}

	public String getName() {
		return (String) attributes.get("name");
	}

	public String getEmail() {
		return (String) attributes.get("email");
	}
}
