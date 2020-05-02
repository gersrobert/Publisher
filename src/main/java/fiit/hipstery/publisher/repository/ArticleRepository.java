package fiit.hipstery.publisher.repository;

import fiit.hipstery.publisher.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {



}
