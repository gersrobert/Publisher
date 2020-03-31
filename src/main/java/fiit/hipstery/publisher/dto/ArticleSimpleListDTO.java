package fiit.hipstery.publisher.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleSimpleListDTO {
	protected List<ArticleSimpleDTO> articles;
	protected int numberOfArticles;

	public List<ArticleSimpleDTO> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleSimpleDTO> articles) {
		this.articles = articles;
	}

	public int getNumberOfArticles() {
		return numberOfArticles;
	}

	public void setNumberOfArticles(int numberOfArticles) {
		this.numberOfArticles = numberOfArticles;
	}
}
