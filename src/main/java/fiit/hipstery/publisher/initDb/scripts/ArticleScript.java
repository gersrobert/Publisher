package fiit.hipstery.publisher.initDb.scripts;

import fiit.hipstery.publisher.entity.*;
import fiit.hipstery.publisher.initDb.InitDbScript;
import fiit.hipstery.publisher.initDb.config.EntityCache;
import fiit.hipstery.publisher.initDb.dto.ArticleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Order(5)
@Profile("initDb")
public class ArticleScript extends InitDbScript {

	@Autowired
	private EntityCache<AppUser> appUserEntityCache;

	@Autowired
	private EntityCache<Publisher> publisherEntityCache;

	@Autowired
	private EntityCache<Article> articleEntityCache;

	private Map<String, Category> categoryMap = new HashMap<>();

	@Override
	@Transactional
	public void run() {
		List<ArticleDTO> articleContent = publisherFaker.getArticleContent();

		articleContent.forEach(articleDTO -> {
			Article article = new Article();
			article.setTitle(articleDTO.getTitle());
			article.setContent(articleDTO.getContent());
			article.setCategories(articleDTO.getCategories().stream().map(name -> {
				Category category;
				if (categoryMap.containsKey(name)) {
					category = categoryMap.get(name);
				} else {
					category = new Category();
					category.setName(name);
					entityManager.persist(category);
					categoryMap.put(name, category);
				}
				return category;
			}).collect(Collectors.toList()));
			article.setPublisher(publisherEntityCache.getRandom("publisher"));

			int likeCount = (int) (Math.random() * (appUserEntityCache.get("appUser").size() * 0.025) * 2);
			article.setLikeCount(likeCount);

			entityManager.persist(article);
			articleEntityCache.append("article", article);

			double random = Math.random();
			if (random > 0) {
				persistRelation(article, AppUserArticleRelation.RelationType.AUTHOR);
			}
			if (random > 0.6) {
				persistRelation(article, AppUserArticleRelation.RelationType.AUTHOR);
			}
			if (random > 0.9) {
				persistRelation(article, AppUserArticleRelation.RelationType.AUTHOR);
			}

			for (int i = 0; i < likeCount; i++) {
				persistRelation(article, AppUserArticleRelation.RelationType.LIKE);
			}
		});
	}

	private void persistRelation(Article article, AppUserArticleRelation.RelationType relationType) {
		AppUser appUser;
		if (relationType == AppUserArticleRelation.RelationType.AUTHOR) {
			appUser = appUserEntityCache.getRandom("author" + article.getPublisher().getName());
		} else {
			appUser = appUserEntityCache.getRandom("appUser");
		}
		AppUserArticleRelation relation = new AppUserArticleRelation();
		relation.setAppUser(appUser);
		relation.setArticle(article);
		relation.setRelationType(relationType);
		entityManager.persist(relation);
	}

}
