package io.vrooms.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
public class Room {

	@Schema(hidden = true)
	@Id
	private String id;

	@Schema(hidden = true)
	private LocalDate createDate;

	@Schema(description = "Name of the room",
			example = "Architecture committee", required = true)
	@Indexed(unique = true)
	private String name;

	@Schema(description = "Type of the room",
			example = "PUBLIC", enumAsRef = true)
	private Type type;

	@Schema(description = "Description of the room",
			example = "This is fantastic room ever!!!")
	private String description;

	@Schema(description = "The room owner reference",
			example = "ta123sd542lj2131b")
	private String ownerId;

	@Schema(description = "Link to preview image",
			example = "http://preview.com/u3a6ssvn")
	private String preview;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public enum Type {
		PUBLIC, PRIVATE
	}
}
