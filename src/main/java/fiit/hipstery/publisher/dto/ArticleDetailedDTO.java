package fiit.hipstery.publisher.dto;

import fiit.hipstery.publisher.entity.AppUser;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleDetailedDTO {

	protected String id;
	protected String title;
	protected String content;
	protected LocalDateTime publishedAt;
	protected List<String> authors;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(LocalDateTime publishedAt) {
		this.publishedAt = publishedAt;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List <String> authors) {
		this.authors = authors;
	}
}
