package fiit.hipstery.publisher.dto;


import com.fasterxml.jackson.annotation.JsonCreator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ArticleInsertDTO {
	private UUID id;
	private List<UUID> authors;
	private String title;
	private String content;

	protected LocalDateTime createdAt;
	protected LocalDateTime updatedAt;

	@JsonCreator
	public ArticleInsertDTO(List<UUID> authors, String title, String content) {
		this.id = UUID.randomUUID();
		this.authors = authors;
		this.title = title;
		this.content = content;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	// TODO title, content, authors...

	public UUID getId() {
		return id;
	}

	public List<UUID> getAuthors() {
		return authors;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() { return updatedAt;	}
}
