package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.ArticleService;
import fiit.hipstery.publisher.dto.*;
import fiit.hipstery.publisher.entity.AppUserArticleRelation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Profile("!jpa")
public class ArticleServiceNativeImpl implements ArticleService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public ArticleDetailedDTO getArticleById(UUID id) {
		List<Object[]> rows = entityManager.createNativeQuery("SELECT a.id," +
				"       a.title," +
				"       a.created_at," +
				"       au.user_name                            AS user_name," +
				"       au.first_name                           AS first_name," +
				"       au.last_name                            AS last_name," +
				"       au.id                                   AS author_id," +
				"       c.name                                  AS c_name," +
				"       c.id                                    AS c_id," +
				"       p.name                                  AS p_name," +
				"       p.id                                    AS p_id," +
				"       a.content                               AS content," +
				"       (SELECT count(relation.id)" +
				"        FROM app_user_article_relation relation" +
				"        WHERE relation.article_id = a.id" +
				"          AND relation.relation_type = 'LIKE') AS like_count" +
				"   FROM article a" +
				"         JOIN app_user_article_relation aar ON a.id = aar.article_id AND aar.relation_type = 'AUTHOR'" +
				"         JOIN app_user au ON aar.app_user_id = au.id" +
				"         LEFT OUTER JOIN article_categories ac on a.id = ac.article_id" +
				"         LEFT OUTER JOIN category c on ac.categories_id = c.id" +
				"         LEFT OUTER JOIN publisher p on a.publisher_id = p.id" +
				"   WHERE a.id=:id")
				.setParameter("id", id.toString()).getResultList();

		ArticleDetailedDTO articleDetailedDTO = new ArticleDetailedDTO();
		Object[] article = rows.get(0);

		articleDetailedDTO.setId((String) article[0]);
		articleDetailedDTO.setTitle((String) article[1]);
		articleDetailedDTO.setPublishedAt(((Timestamp) article[2]).toLocalDateTime());
		articleDetailedDTO.setContent((String) article[11]);
		articleDetailedDTO.setLikeCount(((BigInteger) article[12]).intValue());

		PublisherDTO publisherDTO = new PublisherDTO();
		publisherDTO.setName((String) article[9]);
		publisherDTO.setId((String) article[10]);
		articleDetailedDTO.setPublisher(publisherDTO);

		for (Object[] row : rows) {
			if (articleDetailedDTO.getAuthors() == null) {
				articleDetailedDTO.setAuthors(new HashSet<>());
			}
			if (articleDetailedDTO.getCategories() == null) {
				articleDetailedDTO.setCategories(new HashSet<>());
			}

			AppUserDTO appUserDTO = new AppUserDTO();
			appUserDTO.setUserName((String) row[3]);
			appUserDTO.setFirstName((String) row[4]);
			appUserDTO.setLastName((String) row[5]);
			appUserDTO.setId((String) row[6]);
			articleDetailedDTO.getAuthors().add(appUserDTO);

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setName((String) row[7]);
			categoryDTO.setId((String) row[8]);
			articleDetailedDTO.getCategories().add(categoryDTO);
		}

		return articleDetailedDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public List<ArticleSimpleDTO> getArticles() {
//		List<Object[]> articles = entityManager.createNativeQuery("SELECT " +
//				"   a.id," +
//				"   a.title," +
//				"   a.created_at," +
//				"   string_agg(au.user_name, ';') AS user_name," +
//				"   string_agg(au.first_name, ';') AS first_name," +
//				"   string_agg(au.last_name, ';') AS last_name," +
//				"   string_agg(au.id, ';') AS author_id" +
//				"   FROM article a" +
//				"   JOIN app_user_article_relation aa ON a.id = aa.article_id AND aa.relation_type = '" + AppUserArticleRelation.RelationType.AUTHOR + "'" +
//				"   JOIN app_user au ON aa.app_user_id = au.id" +
//				"   GROUP BY a.id").getResultList();
//		return articles.stream().map(this::mapRowToArticleSimpleDTO).collect(Collectors.toList());
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ArticleSimpleDTO> getArticlesInRange(int lowerIndex, int upperIndex) {
		List<Object[]> resultList = entityManager.createNativeQuery("WITH a AS (" +
				"    SELECT *," +
				"           (SELECT count(relation.id)" +
				"            FROM app_user_article_relation relation" +
				"            WHERE relation.article_id = a.id" +
				"              AND relation.relation_type = 'LIKE') AS like_count" +
				"    FROM article a" +
				"    ORDER BY like_count DESC" +
				"    OFFSET :lowerIndex ROWS" +
				"    FETCH NEXT :upperIndex ROWS ONLY" +
				"   )" +
				"   SELECT a.id," +
				"       a.title," +
				"       a.created_at," +
				"       au.user_name  AS user_name," +
				"       au.first_name AS first_name," +
				"       au.last_name  AS last_name," +
				"       au.id         AS author_id," +
				"       c.name        AS c_name," +
				"       c.id          AS c_id," +
				"       p.name        AS p_name," +
				"       p.id          AS p_id," +
				"       a.like_count  AS like_count" +
				"   FROM a" +
				"         JOIN app_user_article_relation aar ON a.id = aar.article_id AND aar.relation_type = 'AUTHOR'" +
				"         JOIN app_user au ON aar.app_user_id = au.id" +
				"         LEFT OUTER JOIN article_categories ac on a.id = ac.article_id" +
				"         LEFT OUTER JOIN category c on ac.categories_id = c.id" +
				"         LEFT OUTER JOIN publisher p on a.publisher_id = p.id" +
				"   ORDER BY like_count DESC").setParameter(
				"lowerIndex", lowerIndex).setParameter(
				"upperIndex", upperIndex - lowerIndex).getResultList();

		Map<String, ArticleSimpleDTO> map = new LinkedHashMap<>();
		for (Object[] row : resultList) {
			ArticleSimpleDTO articleSimpleDTO = map.get(row[0]);
			if (articleSimpleDTO == null) {
				articleSimpleDTO = new ArticleSimpleDTO();
				articleSimpleDTO.setId((String) row[0]);
				articleSimpleDTO.setTitle((String) row[1]);
				articleSimpleDTO.setPublishedAt(((Timestamp) row[2]).toLocalDateTime());
				articleSimpleDTO.setLikeCount(((BigInteger) row[11]).intValue());

				PublisherDTO publisherDTO = new PublisherDTO();
				publisherDTO.setName((String) row[9]);
				publisherDTO.setId((String) row[10]);
				articleSimpleDTO.setPublisher(publisherDTO);

			}

			if (articleSimpleDTO.getAuthors() == null) {
				articleSimpleDTO.setAuthors(new HashSet<>());
			}
			if (articleSimpleDTO.getCategories() == null) {
				articleSimpleDTO.setCategories(new HashSet<>());
			}

			AppUserDTO appUserDTO = new AppUserDTO();
			appUserDTO.setUserName((String) row[3]);
			appUserDTO.setFirstName((String) row[4]);
			appUserDTO.setLastName((String) row[5]);
			appUserDTO.setId((String) row[6]);
			articleSimpleDTO.getAuthors().add(appUserDTO);

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setName((String) row[7]);
			categoryDTO.setId((String) row[8]);
			articleSimpleDTO.getCategories().add(categoryDTO);

			map.put(articleSimpleDTO.getId(), articleSimpleDTO);
		}

		return map.values();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleSimpleDTO> getArticlesByAuthor(String author) {
//		List<Object[]> resultList = entityManager.createNativeQuery("SELECT " +
//				"   a.id," +
//				"   a.title," +
//				"   a.created_at," +
//				"   au.user_name," +
//				"   au.id AS author_id" +
//				"   FROM article a" +
//				"   JOIN app_user_article_relation aa ON a.id = aa.article_id AND aa.relation_type = '" + AppUserArticleRelation.RelationType.AUTHOR + "'" +
//				"   JOIN app_user au ON aa.app_user_id = au.id" +
//				"   WHERE au.user_name = :authorName").setParameter("authorName", author).getResultList();
//
//		return resultList.stream().map(this::mapRowToArticleSimpleDTO).collect(Collectors.toList());
		return null;
	}

	@Transactional
	@Override
	public boolean insertArticle(ArticleInsertDTO article) {
		AtomicBoolean isWriter = new AtomicBoolean(false);

		for (UUID author : article.getAuthors()) {
			entityManager.createNativeQuery("SELECT " +
					"   r.name " +
					"   FROM app_user users " +
					"   JOIN app_user_roles bind " +
					"   ON users.id = bind.app_user_id " +
					"   JOIN role r ON bind.roles_id = r.id" +
					"   WHERE users.id = :uid").setParameter("uid", author.toString()).getResultList().stream().forEach(role -> {
				System.out.println(role);
				if (role.equals("writer")) {
					System.out.println("je autor");
					isWriter.set(true);
				}
			});
		}

		if (!isWriter.get()) {
			return false;
		}

//		for (String category : article.getCategories()) {
//			entityManager.createNativeQuery("SELECT id "+
//					"FROM category " +
//					"WHERE name = :category;").setParameter("category", category)
//		}

		UUID articleUuid = UUID.randomUUID();
		entityManager.createNativeQuery("INSERT " +
				"   INTO article (id, created_at, state, updated_at, content, title)" +
				"   VALUES (:id, :created_at, 'ACTIVE', :updated_at, :content, :title)"
		).setParameter("id", articleUuid
		).setParameter("created_at", LocalDateTime.now()
		).setParameter("updated_at", LocalDateTime.now()
		).setParameter("content", article.getContent()
		).setParameter("title", article.getTitle()).executeUpdate();
		article.getAuthors().forEach(a -> entityManager.createNativeQuery("INSERT " +
				"   INTO app_user_article_relation (" +
				"   id, created_at, state, updated_at, article_id, app_user_id, relation_type) " +
				"   VALUES (:id, :created_at, 'ACTIVE', :updated_at, :article_id, :authors_id, 'AUTHOR')"
		).setParameter("id", UUID.randomUUID()
		).setParameter("created_at", LocalDateTime.now()
		).setParameter("updated_at", LocalDateTime.now()
		).setParameter("article_id", articleUuid
		).setParameter("authors_id", a).executeUpdate());
		return true;
	}

	@Override
	@Transactional
	public int likeArticle(UUID articleId, UUID userId) {
		entityManager.createNativeQuery("INSERT INTO " +
				"app_user_article_relation (id, created_at, state, updated_at, relation_type, app_user_id, article_id)" +
				"VALUES (:id, :createdAt, :state, :updatedAt, :relationType, :appUserId, :articleId)")
				.setParameter("id", UUID.randomUUID())
				.setParameter("createdAt", LocalDateTime.now())
				.setParameter("state", AppUserArticleRelation.STATE_ACTIVE)
				.setParameter("updatedAt", LocalDateTime.now())
				.setParameter("relationType", AppUserArticleRelation.RelationType.LIKE.toString())
				.setParameter("appUserId", userId.toString())
				.setParameter("articleId", articleId.toString()).executeUpdate();

		entityManager.flush();
		Object likeCount = entityManager.createNativeQuery("SELECT count(id) FROM app_user_article_relation " +
				"WHERE article_id=:articleId and relation_type='LIKE'").setParameter("articleId", articleId.toString()).getSingleResult();

		return ((BigInteger) likeCount).intValue();
	}

	@Override
	@Transactional
	public int unlikeArticle(UUID articleId, UUID userId) {
		entityManager.createNativeQuery("DELETE FROM app_user_article_relation " +
				"WHERE article_id=:articleId " +
				"AND app_user_id=:appUserId " +
				"AND relation_type='LIKE'")
				.setParameter("articleId", articleId.toString())
				.setParameter("appUserId", userId.toString())
				.executeUpdate();

		entityManager.flush();
		Object likeCount = entityManager.createNativeQuery("SELECT count(id) FROM app_user_article_relation " +
				"WHERE article_id=:articleId and relation_type='LIKE'").setParameter("articleId", articleId.toString()).getSingleResult();

		return ((BigInteger) likeCount).intValue();
	}

	@Override
	public List<ArticleSimpleDTO> getArticleListForUser(UUID userId) {
		return null;
	}

	public int getNumberOfArticles() {
		return ((Number) entityManager.createNativeQuery("SELECT COUNT(id) FROM article;").getSingleResult()).intValue();
	}
}
