package fiit.hipstery.publisher.bl.service;

import fiit.hipstery.publisher.dto.AppUserDTO;
import fiit.hipstery.publisher.dto.AppUserDetailedDTO;
import fiit.hipstery.publisher.dto.AppUserWithPasswordDTO;

import java.util.Collection;
import java.util.UUID;

public interface UserService {

    UUID authenticateLogin(String userName, String passwordHash);

    AppUserDTO getAppUser(UUID uuid);

    AppUserDetailedDTO getAppUserDetailed(UUID uuid);

    boolean registerAppUser(AppUserWithPasswordDTO user);

    Collection<String> getActionsForArticle(UUID userId, UUID articleId);
}
