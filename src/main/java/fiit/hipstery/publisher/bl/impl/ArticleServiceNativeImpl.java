package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.ArticleService;
import fiit.hipstery.publisher.dto.*;
import fiit.hipstery.publisher.entity.AppUserArticleRelation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
				"    SELECT *" +
				"    FROM article a" +
				"        OFFSET :lowerIndex ROWS" +
				"    FETCH NEXT :upperIndex ROWS ONLY" +
				")" +
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
				"       p.id          AS p_id" +
				"   FROM a" +
				"         JOIN app_user_article_relation aa ON a.id = aa.article_id AND aa.relation_type = 'AUTHOR'" +
				"         JOIN app_user au ON aa.app_user_id = au.id" +
				"         LEFT OUTER JOIN article_categories ac on a.id = ac.article_id" +
				"         LEFT OUTER JOIN category c on ac.categories_id = c.id" +
				"         JOIN publisher p on a.publisher_id = p.id" +
				"   ORDER BY a.updated_at DESC;").setParameter(
				"lowerIndex", lowerIndex).setParameter(
				"upperIndex", upperIndex - lowerIndex).getResultList();

		HashMap<String, ArticleSimpleDTO> map = new HashMap<>();
		for (Object[] row : resultList) {
			ArticleSimpleDTO articleSimpleDTO = map.get(row[0]);
			if (articleSimpleDTO == null) {
				articleSimpleDTO = new ArticleSimpleDTO();
				articleSimpleDTO.setId((String) row[0]);
				articleSimpleDTO.setTitle((String) row[1]);
				articleSimpleDTO.setPublishedAt(((Timestamp) row[2]).toLocalDateTime());

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
	public List<ArticleSimpleDTO> getArticleListForUser(UUID userId) {
		return null;
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

	private List<CategoryDTO> rowToCategoryDto(Object[] row) {
		List<CategoryDTO> categoryDTOS = new ArrayList<>();
		String[] names = ((String) row[0]).split(";");
		String[] ids = ((String) row[1]).split(";");

		for (int i = 0; i < ids.length; i++) {
			CategoryDTO dto = new CategoryDTO();
			dto.setName(names[i]);
			dto.setId(ids[i]);
			categoryDTOS.add(dto);
		}
		return categoryDTOS;
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

	public int getNumberOfArticles() {
		return ((Number) entityManager.createNativeQuery("SELECT COUNT(id) FROM article;").getSingleResult()).intValue();
	}
}
