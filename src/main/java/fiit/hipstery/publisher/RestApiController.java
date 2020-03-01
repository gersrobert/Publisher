package fiit.hipstery.publisher;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestApiController {

    @GetMapping("hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok().body("hello world!");
    }

}
