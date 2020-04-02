package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.UserService;
import fiit.hipstery.publisher.entity.AppUser;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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
    public void authenticateLogin(String userName, String passwordHash) {

    }

}
