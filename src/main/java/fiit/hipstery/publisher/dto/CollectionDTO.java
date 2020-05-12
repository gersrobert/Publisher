package fiit.hipstery.publisher.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.LocalDateTime;
import java.util.List;

public class CollectionDTO extends AbstractDTO {

	protected AppUserDTO author;

	protected String description;

	protected String title;

	protected List<ArticleSimpleDTO> articles;

	protected LocalDateTime createdAt;

	@JsonGetter
	public AppUserDTO getAuthor() {
		return author;
	}

	@JsonSetter
	public void setAuthor(AppUserDTO author) {
		this.author = author;
	}

	@JsonGetter
	public String getDescription() {
		return description;
	}

	@JsonSetter
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonGetter
	public String getTitle() {
		return title;
	}

	@JsonSetter
	public void setTitle(String title) {
		this.title = title;
	}

	@JsonGetter
	public List<ArticleSimpleDTO> getArticles() {
		return articles;
	}

	@JsonSetter
	public void setArticles(List<ArticleSimpleDTO> articles) {
		this.articles = articles;
	}

	@JsonGetter
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@JsonSetter
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
