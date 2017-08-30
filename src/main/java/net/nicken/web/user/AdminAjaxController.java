package net.nicken.web.user;

import net.nicken.model.Role;
import net.nicken.model.User;
import net.nicken.to.UserTo;
import net.nicken.util.UserUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(UserTo userTo) {
        if (userTo.isNew()){
            super.create(UserUtil.createNewFromTo(userTo));
        }
    }

    @PostMapping(value = "/{id}")
    public void enabled(@PathVariable("id") int id, @RequestParam("enabled") boolean enabled){
        super.enable(id, enabled);
    }
}
