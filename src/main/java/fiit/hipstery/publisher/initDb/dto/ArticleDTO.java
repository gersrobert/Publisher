package fiit.hipstery.publisher.initDb.dto;

import java.util.Collection;

public class ArticleDTO extends ArticleListDTO.ArticleDTO {

	protected Collection<String> categories;

	public Collection<String> getCategories() {
		return categories;
	}

	public void setCategories(Collection<String> categories) {
		this.categories = categories;
	}
}
