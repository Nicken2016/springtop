package net.nicken.web.meal;

import net.nicken.model.Meal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static net.nicken.util.DateTimeUtil.parseLocalDate;
import static net.nicken.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController{

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request){
        super.delete(getId(request));
        return "redirect:meals";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(HttpServletRequest request, Model model){
        model.addAttribute("meal", super.get(getId(request)));
        return "meal";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000));
        return "meal";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String updateOrCreate(HttpServletRequest request){
        String id = request.getParameter("id");
        Meal userMeal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if(userMeal.isNew()){
            super.create(userMeal);
        }else {
            super.update(userMeal, userMeal.getId());
        }

        return "redirect:meals";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String getBetween(HttpServletRequest request, Model model){
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    private int getId(HttpServletRequest request){
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
