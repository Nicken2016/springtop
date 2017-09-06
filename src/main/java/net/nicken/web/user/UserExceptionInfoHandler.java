package net.nicken.web.user;

import net.nicken.web.LocalExceptionInfoHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice(assignableTypes = AbstractUserController.class)
public class UserExceptionInfoHandler extends LocalExceptionInfoHandler{

    public UserExceptionInfoHandler() {
        super("exception.users.duplicate_email");
    }
}
