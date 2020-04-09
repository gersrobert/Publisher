package fiit.hipstery.publisher.bl.service;

import com.github.javafaker.App;
import fiit.hipstery.publisher.dto.AppUserDTO;
import fiit.hipstery.publisher.dto.AppUserWithPasswordDTO;

import java.util.UUID;

public interface UserService {

    UUID authenticateLogin(String userName, String passwordHash);

    AppUserDTO getAppUser(UUID uuid);

    boolean registerAppUser(AppUserWithPasswordDTO user);
}
