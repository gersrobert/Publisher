package fiit.hipstery.publisher.initDb.requests;

import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Article;
import fiit.hipstery.publisher.initDb.dto.ArticleListDTO;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("initDb")
public class ArticlesRequest extends InitDbRequest {

	@Override
	@Transactional
	public void run() {
		ArticleListDTO articleContent = publisherFaker.getArticleContent();

		articleContent.getArticles().forEach(articleDTO -> {
			Article article = new Article();
			article.setTitle(articleDTO.getTitle());
			article.setContent(articleDTO.getContent());


			article.setAuthors(getRandomAppUsers());
		});
	}

	private List<AppUser> getRandomAppUsers() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AppUser> query = cb.createQuery(AppUser.class);
		Root<AppUser> from = query.from(AppUser.class);
//		query.where(cb.in("writer").value(from.get("authors")));
		query.where(cb.lessThan(cb.function("random", Double.class), 0.4));

		List<AppUser> resultList = entityManager.createQuery(query).getResultList();
		return resultList;
	}
}
