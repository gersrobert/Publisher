package fiit.hipstery.publisher.initDb;

import com.github.javafaker.Faker;
import fiit.hipstery.publisher.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Component
@Profile("initDb")
public class AddArticles {

	Logger logger = LoggerFactory.getLogger(AddArticles.class);

	@Autowired
	private List<InitDbScript> initDbScripts;

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
		for (int i = 0; i < 1_000_000; i++) {
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

//		Session hibernateSession = entityManager.unwrap(Session.class);
//		hibernateSession.doWork(connection -> {
//			PreparedStatement articleInsert = connection.prepareStatement("insert into article" +
//					"(id, created_at, state, updated_at, content, like_count, title, publisher_id)" +
//					"values (:p, :p, :p, :p, :p, :p, :p, :p)", new int[] {1, 2, 3, 4, 5, 6, 7 ,8});
//
//			PreparedStatement relationInsert = connection.prepareStatement("insert into app_user_article_relation" +
//					" (id, created_at, state, updated_at, relation_type, app_user_id, article_id)" +
//					" VALUES (:id, :c_at, :state, :up_at, :r_t, :au_t, :a_t)");
//
//			PreparedStatement commentInsert = connection.prepareStatement("insert into comment" +
//					"(id, created_at, state, updated_at, content, article_id, author_id)" +
//					"VALUES (:id, :c_at, :state, :up_at, :content, :a_id, :au_id)");
//
//			for (int i = 0; i < 1_000; i++) {
//				int likeCount = (int) (Math.random() * 100);
//				UUID articleId = UUID.randomUUID();
//				Object publisher = rand(publishers);
//
//				articleInsert.setString(1, articleId.toString());
//				articleInsert.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now().minusDays((long) (Math.random() * 30))));
//				articleInsert.setString(3, "ACTIVE");
//				articleInsert.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
//				articleInsert.setString(5, content);
//				articleInsert.setInt(6, likeCount);
//				articleInsert.setString(7, String.format("%s %s %s %s",
//						faker.rickAndMorty().character(),
//						faker.hacker().verb(),
//						faker.hacker().adjective(),
//						faker.hacker().noun()
//				));
//				articleInsert.setString(8, publisher.toString());
//
//				articleInsert.addBatch();
//
//				if (i % 100 == 0) {
//					articleInsert.executeBatch();
//				}
//			}
//		});

//		for (int i = 0; i < 1_000_000; i++) {
//			int likeCount = (int) (Math.random() * 100);
//
//			UUID articleId = UUID.randomUUID();
//
//			Object publisher = rand(publishers);
//			entityManager.createNativeQuery("insert into article " +
//					"(id, created_at, state, updated_at, content, like_count, title, publisher_id) " +
//					"values (:id, :c_at, :state, :up_at, :content, :likes, :title, :pub_id);")
//					.setParameter("id", articleId)
//					.setParameter("c_at", LocalDateTime.now().minusDays((long) (Math.random() * 30)))
//					.setParameter("state", "ACTIVE")
//					.setParameter("up_at", LocalDateTime.now())
//					.setParameter("content", content)
//					.setParameter("likes", likeCount)
//					.setParameter("title", String.format("%s %s %s %s",
//							faker.rickAndMorty().character(),
//							faker.hacker().verb(),
//							faker.hacker().adjective(),
//							faker.hacker().noun()
//					))
//					.setParameter("pub_id", publisher)
//					.executeUpdate();
//
//			entityManager.createNativeQuery("insert into app_user_article_relation " +
//					" (id, created_at, state, updated_at, relation_type, app_user_id, article_id) " +
//					" VALUES (:id, :c_at, :state, :up_at, :r_t, :au_t, :a_t)")
//					.setParameter("id", UUID.randomUUID())
//					.setParameter("c_at", LocalDateTime.now())
//					.setParameter("state", "ACTIVE")
//					.setParameter("up_at", LocalDateTime.now())
//					.setParameter("r_t", "AUTHOR")
//					.setParameter("au_t", rand(authors.get(publisher)))
//					.setParameter("a_t", articleId)
//					.executeUpdate();
//
//			for (int j = 0; j < likeCount; j++) {
//				entityManager.createNativeQuery("insert into app_user_article_relation " +
//						" (id, created_at, state, updated_at, relation_type, app_user_id, article_id) " +
//						" VALUES (:id, :c_at, :state, :up_at, :r_t, :au_t, :a_t)")
//						.setParameter("id", UUID.randomUUID())
//						.setParameter("c_at", LocalDateTime.now())
//						.setParameter("state", "ACTIVE")
//						.setParameter("up_at", LocalDateTime.now())
//						.setParameter("r_t", "LIKE")
//						.setParameter("au_t", rand(users))
//						.setParameter("a_t", articleId)
//						.executeUpdate();
//			}
//
//			int commentCount = (int) (Math.random() * 4);
//			for (int j = 0; j < commentCount; j++) {
//				entityManager.createNativeQuery("insert into comment " +
//						"(id, created_at, state, updated_at, content, article_id, author_id) " +
//						"VALUES (:id, :c_at, :state, :up_at, :content, :a_id, :au_id)")
//						.setParameter("id", UUID.randomUUID())
//						.setParameter("c_at", LocalDateTime.now())
//						.setParameter("state", "ACTIVE")
//						.setParameter("up_at", LocalDateTime.now())
//						.setParameter("content", faker.rickAndMorty().quote())
//						.setParameter("a_id", articleId)
//						.setParameter("au_id", rand(users))
//						.executeUpdate();
//			}
//		}
	}

	private <T> T rand(List<T> objects) {
		return objects.get((int) (Math.random() * objects.size()));
	}
}
