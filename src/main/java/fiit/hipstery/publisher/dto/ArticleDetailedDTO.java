package fiit.hipstery.publisher.dto;

import fiit.hipstery.publisher.entity.AppUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class ArticleDetailedDTO extends AbstractDTO {

	protected String title;
	protected String content;
	protected LocalDateTime publishedAt;
	protected Set<AppUserDTO> authors;
	protected Set<CategoryDTO> categories;
	protected PublisherDTO publisher;
	protected int likeCount;

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

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
}
