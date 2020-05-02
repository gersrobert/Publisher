package fiit.hipstery.publisher.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CollectionDTO extends AbstractDTO {

	protected AppUserDTO author;

	protected String description;

	protected String title;

	protected List<ArticleSimpleDTO> articles;

	protected LocalDateTime createdAt;

	public AppUserDTO getAuthor() {
		return author;
	}

	public void setAuthor(AppUserDTO author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ArticleSimpleDTO> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleSimpleDTO> articles) {
		this.articles = articles;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
