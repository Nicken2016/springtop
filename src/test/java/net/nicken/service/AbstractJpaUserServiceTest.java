package net.nicken.service;

import net.nicken.model.Role;
import net.nicken.model.User;
import net.nicken.repository.JpaUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

abstract public class AbstractJpaUserServiceTest extends AbstractUserServiceTest{
    @SuppressWarnings("StringJavaAutowiringInspection")
    @Autowired
    private JpaUtil jpaUtil;

    @Before
    @Override
    public void setUp() throws Exception{
        super.setUp();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testValidation() throws Exception{
        validateRootCause(() -> service.save(new User(null, " ", "invalid@yandex.ru", "password", 2000, Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", " ", "password", 2000, Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", "invalid@yandex.ru", " ", 2000, Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", "invalid@yandex.ru", "password", 9, true, Collections.emptySet())), ConstraintViolationException.class);
        validateRootCause(() -> service.save(new User(null, "User", "invalid@yandex.ru", "password", 10001, true, Collections.emptySet())), ConstraintViolationException.class);

    }


}
