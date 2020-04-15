package fiit.hipstery.publisher.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

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
