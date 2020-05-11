package fiit.hipstery.publisher.repository;

import fiit.hipstery.publisher.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
	Role getByName(String name);
}
