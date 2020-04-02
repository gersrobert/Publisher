package fiit.hipstery.publisher.controller;

import fiit.hipstery.publisher.bl.service.ArticleService;
import fiit.hipstery.publisher.bl.service.UserService;
import fiit.hipstery.publisher.dto.LoginRequestDTO;
import fiit.hipstery.publisher.exception.InternalServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
		} catch (Exception e) {
			logger.error("Error getting article list", e);
			throw new InternalServerException(e);
		}

		return ResponseEntity.of(Optional.of(response));
	}

}
