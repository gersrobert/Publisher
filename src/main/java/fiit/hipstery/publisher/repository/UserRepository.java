package fiit.hipstery.publisher.repository;

import fiit.hipstery.publisher.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {


}
