package net.nicken.web;

import net.nicken.AuthorizedUser;
import net.nicken.service.MealService;
import net.nicken.service.UserService;
import net.nicken.util.MealsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {

    @Autowired
    private UserService userService;

    @Autowired
    private MealService mealService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(){
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(Model model){
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request){
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId((userId));
        return "redirect:meals";
    }

    @RequestMapping(value="/meals", method = RequestMethod.GET)
    public String meals(Model model){
        model.addAttribute("meals",
                MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }
}
