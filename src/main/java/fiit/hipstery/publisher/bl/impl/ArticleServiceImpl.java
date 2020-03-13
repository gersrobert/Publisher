package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.ArticleService;
import fiit.hipstery.publisher.dto.ArticleDetailedDTO;
import fiit.hipstery.publisher.dto.ArticleSimpleDTO;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Article;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
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

    @Override
    public List<ArticleSimpleDTO> getArticlesByAuthor(String author) {
        AppUser user = entityManager.createQuery("from AppUser", AppUser.class).getResultList().stream().filter(
                u -> u.getUserName().equals(author)).findFirst().get();
        List<Article> articles = entityManager.createQuery("from Article", Article.class).getResultList().stream(
                ).filter(a -> a.getAuthors().contains(user)).collect(Collectors.toList());
        return articles.stream().map(this::articleToArticleSimpleDTO).collect(Collectors.toList());
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
