package fiit.hipstery.publisher.entity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(indexes = {
		@Index(columnList = "article_id"),
		@Index(columnList = "app_user_id")
})
public class AppUserArticleRelation extends AbstractEntity {

	protected String relationType;

	@ManyToOne
	protected AppUser appUser;

	@ManyToOne
	protected Article article;

	public RelationType getRelationType() {
		return RelationType.valueOf(relationType);
	}

	public void setRelationType(RelationType relationType) {
		this.relationType = relationType.toString();
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public enum RelationType {
		AUTHOR, LIKE, READ
	}
}
