package fiit.hipstery.publisher.controller;

import fiit.hipstery.publisher.bl.service.ArticleService;
import fiit.hipstery.publisher.dto.ArticleDetailedDTO;
import fiit.hipstery.publisher.dto.ArticleInsertDTO;
import fiit.hipstery.publisher.dto.ArticleSimpleDTO;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Article;
import fiit.hipstery.publisher.exception.InternalServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<ArticleDetailedDTO> getArticleById(@PathVariable String id) {
        ArticleDetailedDTO article;
        try {
            article = articleService.getArticleById(UUID.fromString(id));
        } catch (Exception e) {
            logger.error("Error getting article with id: " + id, e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.of(Optional.of(article));
    }

    @GetMapping("")
    public ResponseEntity<List<ArticleSimpleDTO>> getArticles() {
        List<ArticleSimpleDTO> article;
        try {
            article = articleService.getArticles();
        } catch (Exception e) {
            logger.error("Error getting article list", e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.of(Optional.of(article));
    }

    @GetMapping("/index/{lowerIndex}-{upperIndex}")
    public ResponseEntity<List<ArticleSimpleDTO>> getArticlesInRange(
            @PathVariable int lowerIndex, @PathVariable int upperIndex) {
        List<ArticleSimpleDTO> article;
        try {
            article = articleService.getArticlesInRange(lowerIndex, upperIndex);
        } catch (Exception e) {
            logger.error("Error getting article list", e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.of(Optional.of(article));
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<ArticleSimpleDTO>> getArticlesByAuthor(@PathVariable String author) {
        List<ArticleSimpleDTO> article;
        try {
            article = articleService.getArticlesByAuthor(author);
        } catch (Exception e) {
            logger.error("Error getting article list", e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.of(Optional.of(article));
    }

    @GetMapping("forUser/{id}")
    public ResponseEntity<List<ArticleSimpleDTO>> getArticleListForUser(@PathVariable String id) {
        List<ArticleSimpleDTO> article;
        try {
            article = articleService.getArticleListForUser(UUID.fromString(id));
        } catch (Exception e) {
            logger.error("Error getting article list", e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.of(Optional.of(article));
    }

    @PostMapping("insert")
    public ResponseEntity insertArticle(@Valid @RequestBody ArticleInsertDTO body) {
        try {
            System.out.println(body);
            // articleService.insertArticle(article);
        } catch (Exception e) {
            logger.error("Error getting article list", e);
            throw new InternalServerException(e);
        }

        return ResponseEntity.ok().build();
    }
}
