package fiit.hipstery.publisher.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ArticleInsertDTO {
	private List<UUID> authors;
	private String title;
	private String content;

	public List<UUID> getAuthors() {
		return authors;
	}

	@JsonSetter
	public void setAuthors(List<UUID> authors) {
		this.authors = authors;
	}

	public String getTitle() {
		return title;
	}

	@JsonSetter
	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	@JsonSetter
	public void setContent(String content) {
		this.content = content;
	}
}
