package net.nicken.web.user;

import net.nicken.AuthorizedUser;
import net.nicken.model.User;
import net.nicken.service.UserService;
import net.nicken.to.UserTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static net.nicken.util.ValidationUtil.checkIdConsistent;
import static net.nicken.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    private static final Logger log = LoggerFactory.getLogger(AbstractUserController.class);

    @Autowired
    private UserService service;

    public List<User> getAll(){
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id){
        log.info("get = "+id);
        return service.get(id);
    }

    public User create(User user){
        checkNew(user);
        log.info("create "+user);
        return service.save(user);
    }

    public void delete(int id){
        log.info("delete = "+id);
        service.delete(id);
    }

    public void update(User user, int id){
        checkIdConsistent(user, id);
        log.info("update = "+user);
        service.update(user);
    }

    public void update(UserTo userTo, int id){
        log.info("update " + userTo);
        checkIdConsistent(userTo, id);
        service.update(userTo);
    }

    public User getByMail(String email){
        log.info("getByMail = "+email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enabled){
        log.info((enabled ? "enable " : "disable ") + id);
        service.enable(id, enabled);
    }
}
