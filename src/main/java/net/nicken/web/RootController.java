package net.nicken.web;


import net.nicken.AuthorizedUser;
import net.nicken.to.UserTo;
import net.nicken.util.UserUtil;
import net.nicken.web.user.AbstractUserController;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;


@Controller
public class RootController extends AbstractUserController{


    @GetMapping("/")
    public String root(){
        return "redirect:meals";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String users(){
        return "users";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping("/meals")
    public String meals(){
        return "meals";
    }

    @GetMapping("/profile")
    public String profile(){
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status){
        if (!result.hasErrors()){
            try {
                super.update(userTo, AuthorizedUser.id());
                AuthorizedUser.get().update(userTo);
                status.setComplete();
                return "redirect:meals";
            } catch (DataIntegrityViolationException ex){
                result.rejectValue("email", "exception.users.duplicate_email");
            }
        }
        return "profile";
    }

    @GetMapping("/register")
    public String register(ModelMap model){
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model){
        if (!result.hasErrors()){
            try {
                super.create(UserUtil.createNewFromTo(userTo));
                status.setComplete();
                return "redirect:login?message=app.registered&username=" + userTo.getEmail();
            } catch (DataIntegrityViolationException ex) {
                result.rejectValue("email", "exception.users.duplicate_email");
            }
        }
        model.addAttribute("register", true);
        return "profile";
    }
}
