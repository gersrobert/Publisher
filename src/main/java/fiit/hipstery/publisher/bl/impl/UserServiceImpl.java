package fiit.hipstery.publisher.bl.impl;

import fiit.hipstery.publisher.bl.service.UserService;
import fiit.hipstery.publisher.dto.AppUserDTO;
import fiit.hipstery.publisher.dto.AppUserDetailedDTO;
import fiit.hipstery.publisher.dto.AppUserWithPasswordDTO;
import fiit.hipstery.publisher.entity.AppUser;
import fiit.hipstery.publisher.entity.Publisher;
import fiit.hipstery.publisher.entity.Role;
import fiit.hipstery.publisher.repository.ArticleRepository;
import fiit.hipstery.publisher.repository.RoleRepository;
import fiit.hipstery.publisher.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UUID authenticateLogin(String userName, String passwordHash) {
	    UUID userId = userRepository.getFirstByUserNameAndPasswordHash(userName, passwordHash).getId();
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
    	AppUser alreadyExists = userRepository.getFirstByUserName(user.getUserName());
    	if (alreadyExists != null) {
    	    return false;
        }

        Role role = roleRepository.getByName("reader");

	    AppUser newUser = new AppUser();
	    newUser.setFirstName(user.getFirstName());
	    newUser.setLastName(user.getLastName());
        newUser.setPasswordHash(user.getPasswordHash());
        newUser.setUserName(user.getUserName());
        newUser.setRoles(Collections.singletonList(role));

        userRepository.save(newUser);

        return true;
    }

    @Override
    public Collection<String> getActionsForArticle(UUID userId, UUID articleId) {
        AppUser user = entityManager.find(AppUser.class, userId);
        List<AppUser> authors = articleRepository.getAuthors(articleId);
        Publisher publisher = articleRepository.getPublisher(articleId);

        Set<String> actions = new HashSet<>();
        for (Role role : user.getRoles()) {
            if (role.getName().equals("reader")) {
                actions.add("addToCollection");
                actions.add("downloadPdf");
            }
            else if (role.getName().equals("writer")) {
                for (AppUser author : authors) {
                    if (author.getId().equals(userId)) {
                        actions.add("edit");
                        actions.add("delete");
                    }
                }
            }
            else if (role.getName().equals("publisher_owner")) {
                if (publisher.equals(user.getPublisher())) {
                    actions.add("edit");
                    actions.add("delete");
                }
            }
            else if (role.getName().equals("admin")) {
                actions.add("addToCollection");
                actions.add("edit");
                actions.add("delete");
            }
        }

        return actions;
    }
}
