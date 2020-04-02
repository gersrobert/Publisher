package fiit.hipstery.publisher.bl.service;

public interface UserService {

    void insertUser(String firstName, String lastName);

    void authenticateLogin(String userName, String passwordHash);
}
