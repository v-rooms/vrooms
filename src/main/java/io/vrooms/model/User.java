package io.vrooms.model;

import io.openvidu.java.client.OpenViduRole;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {

	@Schema(hidden = true)
	@Id
	private String id;

	@Schema(description = "Name of the user", required = true)
	private String name;

	private String email;

	@Schema(description = "Role of the user",
			example = "PUBLISHER", enumAsRef = true)
	private OpenViduRole role;

	public User() {
	}

	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public OpenViduRole getRole() {
		return role;
	}

	public void setRole(OpenViduRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", role=" + role +
				'}';
	}
}
