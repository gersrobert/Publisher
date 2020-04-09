package fiit.hipstery.publisher.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

public class AppUserWithPasswordDTO extends AppUserDTO {
	protected String passwordHash;

	public String getPasswordHash() {
		return passwordHash;
	}

	@JsonSetter
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}
