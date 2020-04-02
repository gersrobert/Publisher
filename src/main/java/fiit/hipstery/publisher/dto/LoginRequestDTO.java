package fiit.hipstery.publisher.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class LoginRequestDTO {

	protected String username;
	protected String passwordHash;

	@JsonCreator
	public LoginRequestDTO(String username, String passwordHash) {
		this.username = username;
		this.passwordHash = passwordHash;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}
