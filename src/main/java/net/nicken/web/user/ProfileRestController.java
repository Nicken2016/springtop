package net.nicken.web.user;

import net.nicken.AuthorizedUser;
import net.nicken.model.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ProfileRestController.REST_URI)
public class ProfileRestController extends AbstractUserController{

    static final String REST_URI = "/rest/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(int id) {
        return super.get(AuthorizedUser.id());
    }

    @DeleteMapping
    public void delete(int id) {
        super.delete(AuthorizedUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user, @PathVariable("id") int id) {
        super.update(user, AuthorizedUser.id());
    }
}
