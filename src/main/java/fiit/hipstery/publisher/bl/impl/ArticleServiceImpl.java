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
import java.sql.Timestamp;
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

        ArticleDetailedDTO articleDetailedDTO = new ArticleDetailedDTO();
        articleDetailedDTO.setId(article.getId().toString());
        articleDetailedDTO.setAuthor(article.getAuthors().get(0).getUserName());
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

    @Override
    public Article insertArticle(ArticleInsertDTO articleInsertDTO) {
        Article article = new Article();
//        article.setAuthors(authors);
//        article.setTitle(title);
//        article.setContent(articleInsertDTO);
        System.out.println("test123");
        System.out.println(article);
        return article;
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
