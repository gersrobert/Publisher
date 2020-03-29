package fiit.hipstery.publisher.initDb.scripts;

import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.AppUserArticleRelation;
import fiit.hipstery.publisher.entity.Article;
import fiit.hipstery.publisher.initDb.InitDbCsvScript;
import fiit.hipstery.publisher.initDb.InitDbScript;
import fiit.hipstery.publisher.initDb.dto.ArticleListDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Order(3)
@Profile("initDb")
public class ArticleScript extends InitDbScript<Article> {

	@Override
	@Transactional
	public void run() {
		ArticleListDTO articleContent = publisherFaker.getArticleContent();

		articleContent.getArticles().forEach(articleDTO -> {
			Article article = new Article();
			article.setTitle(articleDTO.getTitle());
			article.setContent(articleDTO.getContent());

			entityManager.persist(article);
			assignRandomAuthors(article);
		});
	}

	@SuppressWarnings("unchecked")
	private void assignRandomAuthors(Article article) {
		List<String> authors = entityManager.createNativeQuery("select au.id from app_user au" +
				" join app_user_roles aur on au.id = aur.app_user_id" +
				" join role r on aur.roles_id = r.id" +
				" where r.name='writer'" +
				" order by random()" +
				" limit 1 + (random()*0.05) + (random() * 0.3) + (random()*1.4)").getResultList();

		authors.forEach(author -> {
			AppUser appUser = entityManager.find(AppUser.class, UUID.fromString(author));

			AppUserArticleRelation relation = new AppUserArticleRelation();
			relation.setRelationType(AppUserArticleRelation.RelationType.AUTHOR);
			relation.setArticle(article);
			relation.setAppUser(appUser);
			entityManager.persist(relation);
		});
	}
}
