package io.vrooms.security;

import java.util.Map;

public class OAuthUserInfo {

	public static final String _ID = "_id";
	public static final String EMAIL = "email";
	public static final String NAME = "name";
	public static final String SUB = "sub";

	private Map<String, Object> attributes;

	public OAuthUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getId() {
		return (String) attributes.get(SUB);
	}

	public String getName() {
		return (String) attributes.get(NAME);
	}

	public String getEmail() {
		return (String) attributes.get(EMAIL);
	}
}
