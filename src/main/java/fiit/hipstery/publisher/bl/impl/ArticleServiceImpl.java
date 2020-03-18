package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.ArticleService;
import fiit.hipstery.publisher.dto.ArticleDetailedDTO;
import fiit.hipstery.publisher.dto.ArticleInsertDTO;
import fiit.hipstery.publisher.dto.ArticleSimpleDTO;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Article;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Profile("!sqlOnly")
public class ArticleServiceImpl implements ArticleService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ArticleDetailedDTO getArticleById(UUID id) {
        Article article = entityManager.getReference(Article.class, id);

        // TODO TOTO TREBA KRAJSIE A OVELA :D
        List<String> articleAuthors = Collections.singletonList(article.getAuthors().get(0).getUserName());

        ArticleDetailedDTO articleDetailedDTO = new ArticleDetailedDTO();
        articleDetailedDTO.setId(article.getId().toString());
        articleDetailedDTO.setAuthors(articleAuthors);
        articleDetailedDTO.setTitle(article.getTitle());
        articleDetailedDTO.setContent(article.getContent());
        articleDetailedDTO.setPublishedAt(article.getCreatedAt());
        return articleDetailedDTO;
    }

    @Override
    public List<ArticleSimpleDTO> getArticles() {
        List<Article> articles = entityManager.createQuery("from Article", Article.class).getResultList();
        return articles.stream().map(this::articleToArticleSimpleDTO).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ArticleSimpleDTO> getArticlesInRange(int lowerIndex, int upperIndex) {
        List<Object[]> resultList = entityManager.createNativeQuery("SELECT " +
                "   a.id," +
                "   a.title," +
                "   a.created_at," +
                "   au.user_name" +
                "   FROM article a" +
                "   JOIN article_authors aa ON a.id = aa.article_id" +
                "   JOIN app_user au ON aa.authors_id = au.id" +
                "   ORDER BY a.updated_at DESC" +
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
                "   JOIN article_authors aa ON a.id = aa.article_id" +
                "   JOIN app_user au ON aa.authors_id = au.id" +
                "   WHERE au.user_name = :authorName").setParameter("authorName", author).getResultList();

        return resultList.stream().map(this::mapRowToArticleSimpleDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean insertArticle(ArticleInsertDTO article) {
//        article.setAuthors(authors);
//        article.setTitle(title);
//        article.setContent(articleInsertDTO);
        entityManager.createNativeQuery("INSERT " +
                "   INTO article (id, created_at, state, updated_at, content, title)" +
                "   VALUES (:id, :created_at, 'ACTIVE', :updated_at, :content, :title)"
                ).setParameter("id", article.getId()
                ).setParameter("created_at", article.getCreatedAt()
                ).setParameter("updated_at", article.getUpdatedAt()
                ).setParameter("content", article.getContent()
                ).setParameter("title", article.getTitle()).executeUpdate();
        article.getAuthors().forEach(a -> entityManager.createNativeQuery("INSERT " +
                "   INTO article_authors (article_id, authors_id) " +
                "   VALUES (:article_id, :authors_id)"
                ).setParameter("article_id", article.getId()
                ).setParameter("authors_id", a).executeUpdate());
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
        articleSimpleDTO.setAuthors(List.of((String) row[3]));

        return articleSimpleDTO;
    }

    private ArticleSimpleDTO articleToArticleSimpleDTO(Article article) {
        ArticleSimpleDTO articleSimpleDTO = new ArticleSimpleDTO();
        articleSimpleDTO.setId(article.getId().toString());
        articleSimpleDTO.setAuthors(article.getAuthors().stream().map(AppUser::getUserName).collect(Collectors.toList()));
        articleSimpleDTO.setPublishedAt(article.getCreatedAt());
        articleSimpleDTO.setTitle(article.getTitle());

        return articleSimpleDTO;
    }
}
