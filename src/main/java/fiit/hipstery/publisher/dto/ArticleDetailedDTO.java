package fiit.hipstery.publisher.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class ArticleDetailedDTO extends AbstractDTO {

	protected String title;
	protected String content;
	protected LocalDateTime createdAt;
	protected Set<AppUserDTO> authors;
	protected Set<CategoryDTO> categories;
	protected PublisherDTO publisher;
	protected boolean liked;
	protected int likeCount;
	protected Set<CommentDTO> comments;

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

	public Set<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}
}
