package fiit.hipstery.publisher.controller;

import fiit.hipstery.publisher.bl.service.ArticleService;
import fiit.hipstery.publisher.bl.service.PdfService;
import fiit.hipstery.publisher.dto.*;
import fiit.hipstery.publisher.exception.InternalServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(path = "/article")
public class ArticleController extends AbstractController{

    @Autowired
    private ArticleService articleService;

    @Autowired
    private PdfService pdfService;

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
        ArticleSimpleListDTO response;
        try {
            response = articleService.getArticlesInRange(lowerIndex, upperIndex, UUID.fromString(userId));
        } catch (Exception e) {
            logger.error("Error getting article list in range from " + lowerIndex + " to " + upperIndex, e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.of(Optional.of(response));
    }

    @GetMapping("/author/{authorId}/{lowerIndex}-{upperIndex}")
    public ResponseEntity<ArticleSimpleListDTO> getArticlesByAuthor(
            @PathVariable String authorId, @PathVariable int lowerIndex, @PathVariable int upperIndex, @RequestHeader("Auth-Token") String currentUserId) {
        ArticleSimpleListDTO response;
        try {
            response = articleService.getArticlesByAuthor(UUID.fromString(authorId), UUID.fromString(currentUserId), lowerIndex, upperIndex);
        } catch (Exception e) {
            logger.error("Error getting list of articles written by : " + authorId, e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/publisher/{publisherId}/{page}/{pageSize}")
    public ResponseEntity<ArticleSimpleListDTO> getArticlesByPublisher(
            @PathVariable String publisherId, @PathVariable int page, @PathVariable int pageSize, @RequestHeader("Auth-Token") String currentUserId) {
        ArticleSimpleListDTO response;
        try {
            response = articleService.getArticlesByPublisher(UUID.fromString(publisherId), UUID.fromString(currentUserId), page, pageSize);
        } catch (Exception e) {
            logger.error("Error getting list of articles published by : " + publisherId, e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/insert", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<IdDTO> insertArticle(@RequestBody ArticleInsertDTO article) {
    	String response;
        try {
            response = articleService.insertArticle(article);
        } catch (Exception e) {
            logger.error("Error inserting into article list", e);
            throw new InternalServerException(e);
        }

        if (response == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        } else {
            IdDTO responseDTO = new IdDTO();
            responseDTO.setId(response);
            return ResponseEntity.ok(responseDTO);
        }
    }

    @DeleteMapping("/delete/{articleId}")
    public ResponseEntity deleteArticle(@PathVariable String articleId) {
        boolean response;
        try {
            response = articleService.deleteArticle(UUID.fromString(articleId));
        } catch (Exception e) {
            logger.error("Error deleting article", e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.ok(response);
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

    @GetMapping(value = "export/{articleId}")
    public ResponseEntity<DataDTO> exportArticle(@PathVariable String articleId) {
        DataDTO response = new DataDTO();
        try {
            response.setData(pdfService.generatePdf(UUID.fromString(articleId)));
        } catch (Exception e) {
            logger.error("Error exporting article", e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.ok(response);
    }
}
