package fiit.hipstery.publisher.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class ArticleSimpleDTO extends AbstractDTO {

	protected String title;
	protected LocalDateTime publishedAt;
	protected Set<AppUserDTO> authors;
	protected Set<CategoryDTO> categories;
	protected PublisherDTO publisher;

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

	public Set<AppUserDTO> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<AppUserDTO> authors) {
		this.authors = authors;
	}

	public Set<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(Set<CategoryDTO> categories) {
		this.categories = categories;
	}

	public PublisherDTO getPublisher() {
		return publisher;
	}

	public void setPublisher(PublisherDTO publisher) {
		this.publisher = publisher;
	}
}
