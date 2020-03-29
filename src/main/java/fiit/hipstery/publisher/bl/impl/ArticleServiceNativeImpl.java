package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.ArticleService;
import fiit.hipstery.publisher.dto.AppUserDTO;
import fiit.hipstery.publisher.dto.ArticleDetailedDTO;
import fiit.hipstery.publisher.dto.ArticleInsertDTO;
import fiit.hipstery.publisher.dto.ArticleSimpleDTO;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.AppUserArticleRelation;
import fiit.hipstery.publisher.entity.Article;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Profile("!jpa")
public class ArticleServiceNativeImpl implements ArticleService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public ArticleDetailedDTO getArticleById(UUID id) {
		List<Object[]> articles = entityManager.createNativeQuery("SELECT " +
				"   a.id," +
				"   a.title," +
				"   a.created_at," +
				"   a.content," +
				"   string_agg(au.user_name, ';') AS user_name," +
				"   string_agg(au.first_name, ';') AS first_name," +
				"   string_agg(au.last_name, ';') AS last_name," +
				"   string_agg(au.id, ';') AS author_id" +
				"   FROM article a" +
				"   JOIN app_user_article_relation aa ON a.id = aa.article_id AND aa.relation_type = '" + AppUserArticleRelation.RelationType.AUTHOR + "'" +
				"   JOIN app_user au ON aa.app_user_id = au.id" +
				"   WHERE a.id = :id" +
				"   GROUP BY a.id")
				.setParameter("id", id.toString()).getResultList();

		return mapRowToArticleDetailedDTO(articles.get(0));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleSimpleDTO> getArticles() {
		List<Object[]> articles = entityManager.createNativeQuery("SELECT " +
				"   a.id," +
				"   a.title," +
				"   a.created_at," +
				"   string_agg(au.user_name, ';') AS user_name," +
				"   string_agg(au.first_name, ';') AS first_name," +
				"   string_agg(au.last_name, ';') AS last_name," +
				"   string_agg(au.id, ';') AS author_id" +
				"   FROM article a" +
				"   JOIN app_user_article_relation aa ON a.id = aa.article_id AND aa.relation_type = '" + AppUserArticleRelation.RelationType.AUTHOR + "'" +
				"   JOIN app_user au ON aa.app_user_id = au.id" +
				"   GROUP BY a.id").getResultList();
		return articles.stream().map(this::mapRowToArticleSimpleDTO).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleSimpleDTO> getArticlesInRange(int lowerIndex, int upperIndex) {
		List<Object[]> resultList = entityManager.createNativeQuery("SELECT " +
				"   a.id," +
				"   a.title," +
				"   a.created_at," +
				"   string_agg(au.user_name, ';') AS user_name," +
				"   string_agg(au.first_name, ';') AS first_name," +
				"   string_agg(au.last_name, ';') AS last_name," +
				"   string_agg(au.id, ';') AS author_id" +
				"   FROM article a" +
				"   JOIN app_user_article_relation aa ON a.id = aa.article_id AND aa.relation_type = '" + AppUserArticleRelation.RelationType.AUTHOR + "'" +
				"   JOIN app_user au ON aa.app_user_id = au.id" +
				"   GROUP BY a.id" +
				"   ORDER BY min(a.updated_at) DESC" +
				"   OFFSET :lowerIndex ROWS" +
				"   FETCH NEXT :upperIndex ROWS ONLY").setParameter(
				"lowerIndex", lowerIndex).setParameter(
				"upperIndex", upperIndex - lowerIndex).getResultList();

		return resultList.stream().map(this::mapRowToArticleSimpleDTO).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleSimpleDTO> getArticlesByAuthor(String author) {
		List<Object[]> resultList = entityManager.createNativeQuery("SELECT " +
				"   a.id," +
				"   a.title," +
				"   a.created_at," +
				"   au.user_name," +
				"   au.id AS author_id" +
				"   FROM article a" +
				"   JOIN app_user_article_relation aa ON a.id = aa.article_id AND aa.relation_type = '" + AppUserArticleRelation.RelationType.AUTHOR + "'" +
				"   JOIN app_user au ON aa.app_user_id = au.id" +
				"   WHERE au.user_name = :authorName").setParameter("authorName", author).getResultList();

		return resultList.stream().map(this::mapRowToArticleSimpleDTO).collect(Collectors.toList());
	}

	@Transactional
	@Override
	public boolean insertArticle(ArticleInsertDTO article) {
//		entityManager.createNativeQuery("INSERT " +
//				"   INTO article (id, created_at, state, updated_at, content, title)" +
//				"   VALUES (:id, :created_at, 'ACTIVE', :updated_at, :content, :title)"
//		).setParameter("id", article.getId()
//		).setParameter("created_at", article.getCreatedAt()
//		).setParameter("updated_at", article.getUpdatedAt()
//		).setParameter("content", article.getContent()
//		).setParameter("title", article.getTitle()).executeUpdate();
//		article.getAuthors().forEach(a -> entityManager.createNativeQuery("INSERT " +
//				"   INTO article_authors (article_id, authors_id) " +
//				"   VALUES (:article_id, :authors_id)"
//		).setParameter("article_id", article.getId()
//		).setParameter("authors_id", a).executeUpdate()); TODO
		return true;
	}

	@Override
	public List<ArticleSimpleDTO> getArticleListForUser(UUID userId) {
		return null;
	}

	private ArticleSimpleDTO mapRowToArticleSimpleDTO(Object[] row) {
		ArticleSimpleDTO articleSimpleDTO = new ArticleSimpleDTO();
		articleSimpleDTO.setId((String) row[0]);
		articleSimpleDTO.setTitle((String) row[1]);
		articleSimpleDTO.setPublishedAt(((Timestamp) row[2]).toLocalDateTime());
		articleSimpleDTO.setAuthors(rowToAppUserDto(Arrays.copyOfRange(row, 3, 7)));

		return articleSimpleDTO;
	}

	private List<AppUserDTO> rowToAppUserDto(Object[] row) {
		List<AppUserDTO> appUserDTOS = new ArrayList<>();
		String[] userNames = ((String) row[0]).split(";");
		String[] firstNames = ((String) row[1]).split(";");
		String[] lastNames = ((String) row[2]).split(";");
		String[] ids = ((String) row[3]).split(";");

		for (int i = 0; i < ids.length; i++) {
			AppUserDTO dto = new AppUserDTO();
			dto.setUserName(userNames[i]);
			dto.setFirstName(firstNames[i]);
			dto.setLastName(lastNames[i]);
			dto.setId(ids[i]);
			appUserDTOS.add(dto);
		}
		return appUserDTOS;
	}

	private ArticleDetailedDTO mapRowToArticleDetailedDTO(Object[] row) {
		ArticleDetailedDTO articleDetailedDTO = new ArticleDetailedDTO();
		articleDetailedDTO.setId((String) row[0]);
		articleDetailedDTO.setTitle((String) row[1]);
		articleDetailedDTO.setPublishedAt(((Timestamp) row[2]).toLocalDateTime());
		articleDetailedDTO.setContent((String) row[3]);
		articleDetailedDTO.setAuthors(rowToAppUserDto(Arrays.copyOfRange(row, 4, 8)));

		return articleDetailedDTO;
	}
}
