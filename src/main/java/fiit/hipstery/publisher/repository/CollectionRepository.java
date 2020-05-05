package fiit.hipstery.publisher.repository;

import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CollectionRepository extends JpaRepository<Collection, UUID> {

	List<Collection> getAllByAuthor_Id(UUID author_id);

}
