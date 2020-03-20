package fiit.hipstery.publisher.dto;

import fiit.hipstery.publisher.entity.AppUser;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleDetailedDTO extends AbstractDTO {

	protected String title;
	protected String content;
	protected LocalDateTime publishedAt;
	protected List<AppUserDTO> authors;

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

	public List<AppUserDTO> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AppUserDTO> authors) {
		this.authors = authors;
	}
}
