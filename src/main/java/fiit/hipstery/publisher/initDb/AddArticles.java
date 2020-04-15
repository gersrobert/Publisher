package fiit.hipstery.publisher.initDb;

import com.github.javafaker.Faker;
import fiit.hipstery.publisher.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile("initDb")
public class AddArticles {

	Logger logger = LoggerFactory.getLogger(AddArticles.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private Faker faker;

	@Autowired
	private PlatformTransactionManager transactionManager;

	public void insert() {
		List<Publisher> publishers = entityManager.createQuery("from Publisher ", Publisher.class).getResultList();

		Map<Publisher, List<AppUser>> authors = new LinkedHashMap<>();
		for (Publisher p : publishers) {
			authors.put(p,
					entityManager.createQuery("from AppUser where publisher=:p", AppUser.class)
							.setParameter("p", p).getResultList()
			);
		}

		List<AppUser> users = entityManager.createQuery("from AppUser ", AppUser.class).getResultList();
		List<Category> categories = entityManager.createQuery("from Category ", Category.class).getResultList();

		String content = faker.lorem().paragraph(30)
				+ System.lineSeparator() + System.lineSeparator()
				+ faker.lorem().paragraph(80)
				+ System.lineSeparator() + System.lineSeparator()
				+ faker.lorem().paragraph(50);

		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);

		long time = System.currentTimeMillis();
		for (int i = 0; i < 10_000; i++) {
			if (i % 10_000 == 0) {
				logger.info("executing batch " + i + " in " + (System.currentTimeMillis() - time));
				time = System.currentTimeMillis();
				entityManager.flush();
				transactionManager.commit(transaction);

				transaction = transactionManager.getTransaction(transactionDefinition);
			}

			int likeCount = (int) (Math.random() * 100);
			Publisher publisher = rand(publishers);

			Article article = new Article();
			article.setPublisher(publisher);
			article.setContent(content);
			article.setTitle(String.format("%s %s %s %s",
					faker.rickAndMorty().character(),
					faker.hacker().verb(),
					faker.hacker().adjective(),
					faker.hacker().noun()
			));

			article.setLikeCount(likeCount);
			for (int j = 0; j < likeCount; j++) {
				AppUserArticleRelation relation = new AppUserArticleRelation();
				relation.setArticle(article);
				relation.setAppUser(rand(users));
				relation.setRelationType(AppUserArticleRelation.RelationType.LIKE);
				entityManager.persist(relation);
			}

			AppUserArticleRelation relation = new AppUserArticleRelation();
			relation.setArticle(article);
			relation.setAppUser(rand(authors.get(publisher)));
			relation.setRelationType(AppUserArticleRelation.RelationType.AUTHOR);
			entityManager.persist(relation);

			int commentCount = (int) (Math.random() * 3);
			List<Comment> comments = new ArrayList<>();
			for (int j = 0; j < commentCount; j++) {
				Comment comment = new Comment();
				comment.setArticle(article);
				comment.setAuthor(rand(users));
				comment.setContent(faker.rickAndMorty().quote());
				comments.add(comment);
			}
			article.setComments(comments);

			int categoryCount = (int) (Math.random() * 3);
			List<Category> articleCategories = new ArrayList<>();
			for (int j = 0; j < categoryCount; j++) {
				articleCategories.add(rand(categories));
			}
			article.setCategories(articleCategories);

			entityManager.persist(article);
		}

		entityManager.flush();
		transactionManager.commit(transaction);
	}

	private <T> T rand(List<T> objects) {
		return objects.get((int) (Math.random() * objects.size()));
	}
}
