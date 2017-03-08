package net.nicken.web.user;

import net.nicken.model.User;
import net.nicken.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static net.nicken.util.ValidationUtil.checkIdConsistent;
import static net.nicken.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractUserController.class);

    @Autowired
    private UserService service;

    public List<User> getAll(){
        LOG.info("getAll");
        return service.getAll();
    }

    public User get(int id){
        LOG.info("get = "+id);
        return service.get(id);
    }

    public User create(User user){
        checkNew(user);
        LOG.info("create "+user);
        return service.save(user);
    }

    public void delete(int id){
        LOG.info("delete = "+id);
        service.delete(id);
    }

    public void update(User user, int id){
        checkIdConsistent(user, id);
        LOG.info("update = "+user);
        service.update(user);
    }

    public User getByMail(String email){
        LOG.info("getByMail = "+email);
        return service.getByEmail(email);
    }

}
