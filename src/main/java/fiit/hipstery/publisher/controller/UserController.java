package fiit.hipstery.publisher.controller;

import fiit.hipstery.publisher.bl.service.UserService;
import fiit.hipstery.publisher.dto.AppUserDTO;
import fiit.hipstery.publisher.dto.AppUserWithPasswordDTO;
import fiit.hipstery.publisher.dto.LoginRequestDTO;
import fiit.hipstery.publisher.exception.InternalServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(path = "/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping(value = "/login", headers = "Accept=application/json", produces = "application/json")
	public ResponseEntity<UUID> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		UUID response;
		try {
			 response = userService.authenticateLogin(loginRequestDTO.getUsername(), loginRequestDTO.getPasswordHash());
		} catch (NoResultException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (Exception e) {
			logger.error("Error logging in", e);
			throw new InternalServerException(e);
		}

		return ResponseEntity.of(Optional.of(response));
	}

	@GetMapping(value = "/{uuid}")
	public ResponseEntity<AppUserDTO> getUser(@PathVariable String uuid) {
		AppUserDTO response;
		try {
			response = userService.getAppUser(UUID.fromString(uuid));
		} catch (NoResultException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (Exception e) {
			logger.error("Could not find user", e);
			throw new InternalServerException(e);
		}

		return ResponseEntity.of(Optional.of(response));
	}

	@PostMapping(value = "/register")
	public ResponseEntity registerUser(@RequestBody AppUserWithPasswordDTO user) {
		boolean response;
		try {
			response = userService.registerAppUser(user);
		} catch (Exception e) {
			logger.error("Error registering user", e);
			throw new InternalServerException(e);
		}

		if (response) {
			return new ResponseEntity(HttpStatus.CREATED);
		} else {
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
	}
}
