package fiit.hipstery.publisher.dto;

import com.fasterxml.jackson.annotation.JsonSetter;

public class AppUserDTO extends AbstractDTO {

	protected String firstName;
	protected String lastName;
	protected String userName;

	public String getFirstName() {
		return firstName;
	}

	@JsonSetter
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@JsonSetter
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	@JsonSetter
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
