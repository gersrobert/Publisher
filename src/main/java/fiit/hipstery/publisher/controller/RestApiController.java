package fiit.hipstery.publisher.controller;

import fiit.hipstery.publisher.bl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;

@Controller
public class RestApiController {

    @Autowired
    private UserService userService;

    @GetMapping("hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok().body("hello world!");
    }

    @PostMapping("insertUser/{firstName}/{lastName}")
    @Transactional
    public ResponseEntity insertUser(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        userService.insertUser(firstName, lastName);
        return ResponseEntity.ok().build();
    }

}
