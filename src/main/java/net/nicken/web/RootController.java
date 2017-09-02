package net.nicken.web;

import net.nicken.AuthorizedUser;
import net.nicken.service.MealService;
import net.nicken.util.MealsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class RootController {


    @GetMapping("/")
    public String root(){
        return "redirect:meals";
    }

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
}
