package fiit.hipstery.publisher.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class ArticleSimpleListDTO {
	protected Collection<ArticleSimpleDTO> articles;
	protected int numberOfArticles;

	public Collection<ArticleSimpleDTO> getArticles() {
		return articles;
	}

	public void setArticles(Collection<ArticleSimpleDTO> articles) {
		this.articles = articles;
	}

	public int getNumberOfArticles() {
		return numberOfArticles;
	}

	public void setNumberOfArticles(int numberOfArticles) {
		this.numberOfArticles = numberOfArticles;
	}
}
