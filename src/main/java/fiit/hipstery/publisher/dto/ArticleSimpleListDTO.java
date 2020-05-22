package fiit.hipstery.publisher.dto;

import java.util.Collection;

public class ArticleSimpleListDTO {
	protected Collection<ArticleSimpleDTO> articles;
	protected boolean hasMore;

	public Collection<ArticleSimpleDTO> getArticles() {
		return articles;
	}

	public void setArticles(Collection<ArticleSimpleDTO> articles) {
		this.articles = articles;
	}

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
}
