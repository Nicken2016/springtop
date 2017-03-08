package net.nicken.web.user;

import net.nicken.AuthorizedUser;
import net.nicken.model.User;
import org.springframework.stereotype.Controller;

@Controller
public class ProfileRestController extends AbstractUserController{
    @Override
    public User get(int id) {
        return super.get(AuthorizedUser.id());
    }

    @Override
    public void delete(int id) {
        super.delete(AuthorizedUser.id());
    }

    @Override
    public void update(User user, int id) {
        super.update(user, AuthorizedUser.id());
    }
}
