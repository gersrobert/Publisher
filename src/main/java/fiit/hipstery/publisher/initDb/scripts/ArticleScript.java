package fiit.hipstery.publisher.initDb.scripts;

import fiit.hipstery.publisher.entity.*;
import fiit.hipstery.publisher.initDb.InitDbScript;
import fiit.hipstery.publisher.initDb.dto.ArticleListDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Component
@Order(5)
@Profile("initDb")
public class ArticleScript extends InitDbScript {

	@Override
	@Transactional
	public void run() {
		ArticleListDTO articleContent = publisherFaker.getArticleContent();
		List<Category> categories = entityManager.createQuery("from Category ", Category.class).getResultList();
		List<Publisher> publishers = entityManager.createQuery("from Publisher ", Publisher.class).getResultList();

		articleContent.getArticles().forEach(articleDTO -> {
			Article article = new Article();
			article.setTitle(articleDTO.getTitle());
			article.setContent(articleDTO.getContent());
			article.setCategories(categories);
			article.setPublisher(publishers.get((int) (Math.random() * publishers.size())));

			entityManager.persist(article);
			assignRandomAuthors(article);
		});
	}

	@SuppressWarnings("unchecked")
	private void assignRandomAuthors(Article article) {
		List<String> authors = entityManager.createNativeQuery("with rand as (select random() as val) select au.id from app_user au" +
				"   join app_user_roles aur on au.id = aur.app_user_id" +
				"   join role r on aur.roles_id = r.id" +
				"   where r.name='writer'" +
				"   order by random()" +
				"   limit" +
				"       case when ((select val from rand) > 0.9) then 3" +
				"       when ((select val from rand) > 0.6) then 2" +
				"       else 1 end")
				.getResultList();

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
