package fiit.hipstery.publisher.bl.service;

import fiit.hipstery.publisher.dto.*;

import java.io.IOException;
import java.util.UUID;

public interface ArticleService {

    ArticleDetailedDTO getArticleById(UUID id, UUID currentUser) throws IOException;

    ArticleSimpleListDTO getArticles(FilterCriteria filterCriteria, UUID currentUser);

    ArticleSimpleListDTO getArticlesInRange(int lowerIndex, int upperIndex, UUID currentUser);

    ArticleSimpleListDTO getArticlesByAuthor(UUID authorId, UUID currentUserId, int lowerIndex, int upperIndex);

    ArticleSimpleListDTO getArticlesByPublisher(UUID publisherId, UUID currentUserId, int page, int pageSize);

    String insertArticle(ArticleInsertDTO article);

    boolean deleteArticle(UUID articleId);

    void insertComment(CommentInsertDTO comment);

    int likeArticle(UUID articleId, UUID userId);

    int unlikeArticle(UUID articleId, UUID userId);

    int getNumberOfArticles();
}
