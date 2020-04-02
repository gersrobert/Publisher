package fiit.hipstery.publisher.controller;

import fiit.hipstery.publisher.dto.LoginRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping(path = "/user")
public class UserController extends AbstractController {

	@PostMapping("/login")
	public ResponseEntity<UUID> login(@RequestBody LoginRequestDTO loginRequestDTO) {
		throw new RuntimeException("not yet implemented");
	}

}
