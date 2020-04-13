package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.UserService;
import fiit.hipstery.publisher.dto.AppUserDTO;
import fiit.hipstery.publisher.dto.AppUserDetailedDTO;
import fiit.hipstery.publisher.dto.AppUserWithPasswordDTO;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.AppUserArticleRelation;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public UUID authenticateLogin(String userName, String passwordHash) {
        UUID userId = UUID.fromString((String) entityManager.createNativeQuery("SELECT " +
                "id " +
                "FROM app_user " +
                "WHERE user_name = :userName AND " +
                "password_hash = :passwordHash").setParameter("userName", userName
            ).setParameter("passwordHash", passwordHash).getSingleResult());

        // SELECT id FROM app_user WHERE user_name = 'Supah_Hancock_X_bcc1d3' AND password_hash = 'heslo';
        return userId;
    }

    @Override
    public AppUserDTO getAppUser(UUID uuid) {
        AppUser appUser = entityManager.find(AppUser.class, uuid);
        if (appUser == null) {
            throw new NoResultException();
        }

        AppUserDTO dto = new AppUserDTO();
        dto.setId(appUser.getId().toString());
        dto.setFirstName(appUser.getFirstName());
        dto.setLastName(appUser.getLastName());
        dto.setUserName(appUser.getUserName());
        return dto;
    }

    @Override
    public AppUserDetailedDTO getAppUserDetailed(UUID uuid) {
        List<String> roles = new ArrayList<>();

        AppUser appUser = entityManager.find(AppUser.class, uuid);
        AppUserDetailedDTO dto = new AppUserDetailedDTO();
        dto.setId(appUser.getId().toString());
        dto.setFirstName(appUser.getFirstName());
        dto.setLastName(appUser.getLastName());
        dto.setUserName(appUser.getUserName());
        dto.setCreatedAt(appUser.getCreatedAt());

        appUser.getRoles().forEach(role -> roles.add(role.getName()));
        dto.setRoles(roles);
        return dto;
    }


    @Override
    @Transactional
    public boolean registerAppUser(AppUserWithPasswordDTO user) {
    	int alreadyExists = ((Number) entityManager.createNativeQuery("SELECT COUNT(1) " +
                "FROM app_user WHERE user_name = :user_name")
                .setParameter("user_name", user.getUserName()).getSingleResult()).intValue();

    	if (alreadyExists == 1) {
    	    return false;
        }

    	UUID userId = UUID.randomUUID();
        entityManager.createNativeQuery("INSERT INTO " +
                "app_user (id, created_at, state, updated_at, first_name, last_name, password_hash, user_name)" +
                "VALUES (:id, :createdAt, :state, :updatedAt, :firstName, :lastName, :passwordHash, :userName)")
                .setParameter("id", userId)
                .setParameter("createdAt", LocalDateTime.now())
                .setParameter("state", AppUserArticleRelation.STATE_ACTIVE)
                .setParameter("updatedAt", LocalDateTime.now())
                .setParameter("firstName", user.getFirstName())
                .setParameter("lastName", user.getLastName())
                .setParameter("passwordHash", user.getPasswordHash())
                .setParameter("userName", user.getUserName()).executeUpdate();

        UUID roleId = UUID.fromString((String) entityManager.createNativeQuery("SELECT id "+
                "FROM role " +
                "WHERE name = 'reader'").getSingleResult());

        entityManager.createNativeQuery("INSERT " +
                "INTO app_user_roles (app_user_id, roles_id) " +
                "VALUES (:userId, :roleId)")
        .setParameter("userId", userId)
        .setParameter("roleId", roleId).executeUpdate();

        return true;
    }
}
