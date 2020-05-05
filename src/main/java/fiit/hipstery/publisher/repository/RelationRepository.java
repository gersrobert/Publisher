package fiit.hipstery.publisher.repository;

import fiit.hipstery.publisher.entity.AppUserArticleRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RelationRepository extends JpaRepository<AppUserArticleRelation, UUID> {

	boolean existsByAppUser_IdAndArticle_IdAndRelationType(UUID appUser_id, UUID article_id, String relationType);
}
