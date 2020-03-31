package fiit.hipstery.publisher.bl.service;

import fiit.hipstery.publisher.dto.ArticleDetailedDTO;
import fiit.hipstery.publisher.dto.ArticleInsertDTO;
import fiit.hipstery.publisher.dto.ArticleSimpleDTO;
import fiit.hipstery.publisher.entity.Article;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ArticleService {

    ArticleDetailedDTO getArticleById(UUID id) throws IOException;

    List<ArticleSimpleDTO> getArticles();

    List<ArticleSimpleDTO> getArticlesInRange(int lowerIndex, int upperIndex);

    List<ArticleSimpleDTO> getArticlesByAuthor(String author);

    List<ArticleSimpleDTO> getArticleListForUser(UUID userId);

    boolean insertArticle(ArticleInsertDTO article);

    int getNumberOfArticles();
}
