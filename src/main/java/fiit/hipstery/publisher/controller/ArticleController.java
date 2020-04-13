package fiit.hipstery.publisher.controller;

import fiit.hipstery.publisher.bl.service.ArticleService;
import fiit.hipstery.publisher.dto.*;
import fiit.hipstery.publisher.exception.InternalServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(path = "/article")
public class ArticleController extends AbstractController{

    @Autowired
    private ArticleService articleService;

    Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @GetMapping("/id/{id}")
    public ResponseEntity<ArticleDetailedDTO> getArticleById(@PathVariable String id, @RequestHeader("Auth-Token") String userId) {
        ArticleDetailedDTO article;
        try {
            article = articleService.getArticleById(UUID.fromString(id), UUID.fromString(userId));
        } catch (Exception e) {
            logger.error("Error getting article with id: " + id, e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.of(Optional.of(article));
    }

    @GetMapping("")
    public ResponseEntity<ArticleSimpleListDTO> getArticles(@RequestHeader("Auth-Token") String userId,
            @RequestParam(required = false, defaultValue = "") String author,
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "") String category,
            @RequestParam(required = false, defaultValue = "") String publisher,
            @RequestParam(required = false, defaultValue = "0") Integer lowerIndex,
            @RequestParam(required = false, defaultValue = "-1") Integer upperIndex) {

        FilterCriteria criteria = new FilterCriteria();
        criteria.setAuthor(author);
        criteria.setTitle(title);
        criteria.setCategory(category);
        criteria.setPublisher(publisher);
        criteria.setLowerIndex(lowerIndex);
        criteria.setUpperIndex(upperIndex);

        ArticleSimpleListDTO response;
        try {
            response = articleService.getArticles(criteria, UUID.fromString(userId));
        } catch (Exception e) {
            logger.error("Error getting article list", e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.of(Optional.of(response));
    }

    @GetMapping("/index/{lowerIndex}-{upperIndex}")
    public ResponseEntity<ArticleSimpleListDTO> getArticlesInRange(
            @PathVariable int lowerIndex, @PathVariable int upperIndex, @RequestHeader("Auth-Token") String userId) {
        ArticleSimpleListDTO response = new ArticleSimpleListDTO();
        try {
            response.setArticles(articleService.getArticlesInRange(lowerIndex, upperIndex, UUID.fromString(userId)));
            response.setNumberOfArticles(articleService.getNumberOfArticles());
        } catch (Exception e) {
            logger.error("Error getting article list in range from " + lowerIndex + " to " + upperIndex, e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.of(Optional.of(response));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<ArticleSimpleListDTO> getArticlesByAuthor(
            @PathVariable String authorId, @RequestHeader("Auth-Token") String currentUserId) {
        ArticleSimpleListDTO response = new ArticleSimpleListDTO();
        try {
            response.setArticles(articleService.getArticlesByAuthor(UUID.fromString(authorId), UUID.fromString(currentUserId)));
            response.setNumberOfArticles(articleService.getNumberOfArticles());
        } catch (Exception e) {
            logger.error("Error getting list of articles written by : " + authorId, e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.of(Optional.of(response));
    }

    @GetMapping("/forUser/{id}")
    public ResponseEntity<List<ArticleSimpleDTO>> getArticleListForUser(@PathVariable String id) {
        List<ArticleSimpleDTO> article;
        try {
            article = articleService.getArticleListForUser(UUID.fromString(id));
        } catch (Exception e) {
            logger.error("Error getting some strange bobo shit", e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.of(Optional.of(article));
    }

    @PostMapping(value = "/insert", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity insertArticle(@RequestBody ArticleInsertDTO article) {
    	boolean response;
        try {
            response = articleService.insertArticle(article);
        } catch (Exception e) {
            logger.error("Error inserting into article list", e);
            throw new InternalServerException(e);
        }

        if (response) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(value = "/insert/comment", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity insertComment(@RequestBody CommentInsertDTO comment) {
        try {
            articleService.insertComment(comment);
        } catch (Exception e) {
            logger.error("Error commenting article", e);
            throw new InternalServerException(e);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(value = "like/{articleId}")
    public ResponseEntity<Integer> likeArticle(@RequestHeader("Auth-Token") String userId, @PathVariable String articleId) {
        int response;
        try {
            response = articleService.likeArticle(UUID.fromString(articleId), UUID.fromString(userId));
        } catch (Exception e) {
            logger.error("Error liking article", e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "unlike/{articleId}")
    public ResponseEntity<Integer> unlikeArticle(@RequestHeader("Auth-Token") String userId, @PathVariable String articleId) {
        int response;
        try {
            response = articleService.unlikeArticle(UUID.fromString(articleId), UUID.fromString(userId));
        } catch (Exception e) {
            logger.error("Error unliking article", e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.ok(response);
    }
}
