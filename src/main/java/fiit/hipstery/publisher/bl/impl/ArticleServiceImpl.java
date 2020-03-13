package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.ArticleService;
import fiit.hipstery.publisher.dto.ArticleDetailedDTO;
import fiit.hipstery.publisher.dto.ArticleSimpleDTO;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Article;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ArticleDetailedDTO getArticleById(UUID id) {
        Article article = entityManager.getReference(Article.class, id);

        ArticleDetailedDTO articleDetailedDTO = new ArticleDetailedDTO();
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
    public List<ArticleSimpleDTO> getArticlesByAuthor(String author) {
        List<Object[]> resultList = entityManager.createNativeQuery("select " +
                "   a.id," +
                "   a.title," +
                "   a.created_at," +
                "   au.user_name," +
                "   au.id as author_id" +
                "   from article a" +
                "   join article_authors aa on a.id = aa.article_id" +
                "   join app_user au on aa.authors_id = au.id" +
                "   where au.user_name = :authorName").setParameter("authorName", author).getResultList();

        return resultList.stream().map(this::mapRowToArticleSimpleDTO).collect(Collectors.toList());
    }

    private ArticleSimpleDTO mapRowToArticleSimpleDTO(Object[] row) {
        ArticleSimpleDTO articleSimpleDTO = new ArticleSimpleDTO();
        articleSimpleDTO.setTitle((String) row[1]);
        articleSimpleDTO.setPublishedAt(((Timestamp) row[2]).toLocalDateTime());
        articleSimpleDTO.setAuthors(List.of((String) row[3]));
        return articleSimpleDTO;
    }

    @Override
    public List<ArticleSimpleDTO> getArticleListForUser(UUID userId) {
        return null;
    }

    private ArticleSimpleDTO articleToArticleSimpleDTO(Article article) {
        ArticleSimpleDTO articleSimpleDTO = new ArticleSimpleDTO();
        articleSimpleDTO.setAuthors(article.getAuthors().stream().map(AppUser::getUserName).collect(Collectors.toList()));
        articleSimpleDTO.setPublishedAt(article.getCreatedAt());
        articleSimpleDTO.setTitle(article.getTitle());

        return articleSimpleDTO;
    }
}
