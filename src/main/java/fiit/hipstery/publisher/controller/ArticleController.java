package fiit.hipstery.publisher.controller;

import fiit.hipstery.publisher.bl.service.ArticleService;
import fiit.hipstery.publisher.dto.ArticleDetailedDTO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping(path = "/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Value("classpath:static/loremipsum.txt")
    private Resource loremIpsum;

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDetailedDTO> getArticleById(@PathVariable String id) throws IOException {
        ArticleDetailedDTO articleDetailedDTO = new ArticleDetailedDTO();
        articleDetailedDTO.setAuthor("Carrie Lam");
        articleDetailedDTO.setTitle("Bobo je smutny");
        articleDetailedDTO.setContent(FileUtils.readFileToString(loremIpsum.getFile(), StandardCharsets.UTF_8));
        articleDetailedDTO.setPublishedAt(LocalDateTime.now());
        return ResponseEntity.of(Optional.of(articleDetailedDTO));
    }

}
