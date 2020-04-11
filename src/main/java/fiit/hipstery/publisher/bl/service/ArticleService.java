package fiit.hipstery.publisher.bl.service;

import fiit.hipstery.publisher.dto.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ArticleService {

    ArticleDetailedDTO getArticleById(UUID id, UUID currentUser) throws IOException;

    ArticleSimpleListDTO getArticles(FilterCriteria filterCriteria, UUID currentUser);

    Collection<ArticleSimpleDTO> getArticlesInRange(int lowerIndex, int upperIndex, UUID currentUser);

    List<ArticleSimpleDTO> getArticlesByAuthor(String author);

    List<ArticleSimpleDTO> getArticleListForUser(UUID userId);

    boolean insertArticle(ArticleInsertDTO article);

    int likeArticle(UUID articleId, UUID userId);

    int unlikeArticle(UUID articleId, UUID userId);

    int getNumberOfArticles();
}
