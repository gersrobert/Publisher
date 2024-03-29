package fiit.hipstery.publisher.repository;

import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Article;
import fiit.hipstery.publisher.entity.Publisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

	List<Article> getAllByIdIn(Collection<UUID> id);

	List<Article> getAllByPublisherIdOrderByLikeCountDesc(UUID publisher_id, Pageable page);

	@Query("select rel.appUser from AppUserArticleRelation rel where rel.article.id = :article and rel.relationType='AUTHOR'")
	List<AppUser> getAuthors(@Param("article") UUID articleId);

	default Publisher getPublisher(UUID articleId) {
		return getOne(articleId).getPublisher();
	}
}
