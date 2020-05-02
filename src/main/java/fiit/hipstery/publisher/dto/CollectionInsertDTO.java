package fiit.hipstery.publisher.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class CollectionInsertDTO extends AbstractDTO {

	protected String title;
	protected String description;
	protected List<String> articles;
	protected String author;

	@JsonGetter
	public String getTitle() {
		return title;
	}

	@JsonSetter
	public void setTitle(String title) {
		this.title = title;
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
	public List<String> getArticles() {
		return articles;
	}

	@JsonSetter
	public void setArticles(List<String> articles) {
		this.articles = articles;
	}

	@JsonGetter
	public String getAuthor() {
		return author;
	}

	@JsonSetter
	public void setAuthor(String author) {
		this.author = author;
	}
}
