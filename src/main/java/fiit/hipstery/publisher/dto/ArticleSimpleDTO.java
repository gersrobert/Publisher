package fiit.hipstery.publisher.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class ArticleSimpleDTO extends AbstractDTO {

	protected String title;
	protected LocalDateTime createdAt;
	protected Set<AppUserDTO> authors;
	protected Set<CategoryDTO> categories;
	protected PublisherDTO publisher;
	protected int likeCount;
	protected boolean liked;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}
}
