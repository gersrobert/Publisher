package fiit.hipstery.publisher.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleSimpleDTO extends AbstractDTO {

	protected String title;
	protected LocalDateTime publishedAt;
	protected List<AppUserDTO> authors;

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

	public List<AppUserDTO> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AppUserDTO> authors) {
		this.authors = authors;
	}
}
