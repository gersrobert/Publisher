package fiit.hipstery.publisher;

import fiit.hipstery.publisher.entity.AppUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Controller
public class RestApiController {

    @PersistenceContext
    protected EntityManager entityManager;

    @GetMapping("hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok().body("hello world!");
    }

    @GetMapping("insertUser/{firstName}/{lastName}")
    @Transactional
    public ResponseEntity insertUser(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        AppUser appUser = new AppUser();
        appUser.setFirstName(firstName);
        appUser.setLastName(lastName);

        entityManager.persist(appUser);

        return ResponseEntity.ok().build();
    }

}
