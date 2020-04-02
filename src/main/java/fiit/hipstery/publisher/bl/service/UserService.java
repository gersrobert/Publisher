package fiit.hipstery.publisher.bl.service;

import java.util.UUID;

public interface UserService {

    void insertUser(String firstName, String lastName);

    UUID authenticateLogin(String userName, String passwordHash);
}
