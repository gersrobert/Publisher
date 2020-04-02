package fiit.hipstery.publisher.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

public class LoginRequestDTO {

	protected String username;
	protected String passwordHash;

	public String getUsername() {
		return username;
	}

	@JsonSetter
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	@JsonSetter
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}
