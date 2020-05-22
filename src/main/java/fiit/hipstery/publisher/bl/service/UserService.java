package fiit.hipstery.publisher.bl.service;

import fiit.hipstery.publisher.dto.AppUserDTO;
import fiit.hipstery.publisher.dto.AppUserDetailedDTO;
import fiit.hipstery.publisher.dto.AppUserWithPasswordDTO;

import java.util.Collection;
import java.util.UUID;

public interface UserService {

    /**
     * Authenticate user based on userName and password
     * @param userName
     * @param passwordHash sha256 hash of password for maximum security :D
     * @return id of the logged user
     */
    UUID authenticateLogin(String userName, String passwordHash);

    /**
     * Get basic info about a user
     * @param uuid user id
     * @return dto containing user data
     */
    AppUserDTO getAppUser(UUID uuid);

    /**
     * Get detailed info about a user
     * @param uuid user id
     * @return dto containing user data
     */
    AppUserDetailedDTO getAppUserDetailed(UUID uuid);

    /**
     * Create a new user
     * @param user dto containing user data
     * @return true is successful
     */
    boolean registerAppUser(AppUserWithPasswordDTO user);

    /**
     * Get a list of actions, which the currently logged in user can perform on this aricle
     * @param userId id of the logged in user
     * @param articleId id of the article
     * @return List of keys describing actions
     */
    Collection<String> getActionsForArticle(UUID userId, UUID articleId);
}
