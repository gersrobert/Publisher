package fiit.hipstery.publisher.initDb.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Collection;

public class ArticleDTO extends ArticleListDTO.ArticleDTO {

	protected Collection<String> categories;

	protected int likeCount;

	@JsonGetter
	public Collection<String> getCategories() {
		return categories;
	}

	@JsonSetter
	public void setCategories(Collection<String> categories) {
		this.categories = categories;
	}

	@JsonGetter
	public int getLikeCount() {
		return likeCount;
	}

	@JsonSetter
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
}
