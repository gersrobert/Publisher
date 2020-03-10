package fiit.hipstery.publisher.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleSimpleDTO {

	protected String title;
	protected LocalDateTime publishedAt;
	protected List<String> authors;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
}
