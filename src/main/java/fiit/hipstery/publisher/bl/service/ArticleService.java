package fiit.hipstery.publisher.bl.service;

import fiit.hipstery.publisher.dto.*;

import java.io.IOException;
import java.util.UUID;

public interface ArticleService {

    /**
     * @param id is unique Id of article
     * @param currentUser is unique Id of currently logged in user
     * @return detailed version of article
     */
    ArticleDetailedDTO getArticleById(UUID id, UUID currentUser) throws IOException;

    /**
     * Filters articles according to filter.
     * @param filterCriteria Object that contains filters, also contains bounds of articles
     * @param currentUser is unique Id of currently logged in user
     * @return array of simple versions of filtered articles within preset bounds
     */
    ArticleSimpleListDTO getArticles(FilterCriteria filterCriteria, UUID currentUser);

    /**
     * Fetch all articles in range | it is faster then using getArticles with filter
     * @param lowerIndex lower bound of desired articles
     * @param upperIndex upper bound of desired articles
     * @param currentUser is unique Id of currently logged in user
     * @return array of simple versions of articles within preset bounds
     */
    ArticleSimpleListDTO getArticlesInRange(int lowerIndex, int upperIndex, UUID currentUser);

    /**
     * Fetch all articles from desired author | it is faster then using getArticles with filter
     * @param authorId is unique Id of desired author
     * @param currentUserId is unique Id of currently logged in user
     * @param lowerIndex lower bound of desired articles
     * @param upperIndex upper bound of desired articles
     * @return array of simple versions of articles within preset bounds from desired author
     */
    ArticleSimpleListDTO getArticlesByAuthor(UUID authorId, UUID currentUserId, int lowerIndex, int upperIndex);

    /**
     * Fetch all articles from desired publisher | it is faster then using getArticles with filter
     * @param publisherId is unique Id of desired publisher
     * @param currentUserId is unique Id of currently logged in user
     * @param page Index of desired page
     * @param pageSize number of articles in one page
     * @return array of simple versions of articles within preset bounds from desired publisher
     */
    ArticleSimpleListDTO getArticlesByPublisher(UUID publisherId, UUID currentUserId, int page, int pageSize);

    /**
     * Inserts article into table of articles
     * @param article Detailed version of article
     * @return UUID of new article
     */
    String insertArticle(ArticleInsertDTO article);

    /**
     * Soft deletes desired article
     * @param articleId is unique Id of desired article
     * @return true if softdelete was successful
     */
    boolean deleteArticle(UUID articleId);

    /**
     * Insert comment into table of comments, every object of comment includes id of article to which it belongs to.
     * @param comment new comment
     */
    void insertComment(CommentInsertDTO comment);

    /**
     * Like article
     * @param articleId is unique Id of liked article
     * @param userId is unique Id of user
     * @return number of likes on article
     */
    int likeArticle(UUID articleId, UUID userId);

    /**
     * Unlike article
     * @param articleId is unique Id of liked article
     * @param userId is unique Id of user
     * @return number of likes on article
     */
    int unlikeArticle(UUID articleId, UUID userId);

    /**
     * @return Number of articles
     */
    int getNumberOfArticles();
}
