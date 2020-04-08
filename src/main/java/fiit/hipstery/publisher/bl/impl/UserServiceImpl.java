package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.UserService;
import fiit.hipstery.publisher.dto.AppUserDTO;
import fiit.hipstery.publisher.entity.AppUser;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public void insertUser(String firstName, String lastName) {
        AppUser user = new AppUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);

        entityManager.persist(user);
    }

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
        AppUserDTO dto = new AppUserDTO();
        dto.setId(appUser.getId().toString());
        dto.setFirstName(appUser.getFirstName());
        dto.setLastName(appUser.getLastName());
        dto.setUserName(appUser.getUserName());
        return dto;
    }

    @Override
    public boolean registerAppUser(AppUserDTO user) {
        System.out.println(user.getUserName());
        return true;
    }
}
