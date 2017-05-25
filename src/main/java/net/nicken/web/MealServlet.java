package net.nicken.web;

import net.nicken.AuthorizedUser;
import net.nicken.Profiles;
import net.nicken.model.Meal;
import net.nicken.repository.MealRepository;
import net.nicken.util.DateTimeUtil;
import net.nicken.util.MealsUtil;
import net.nicken.web.meal.MealRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet{
private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private ClassPathXmlApplicationContext springContext;
    private MealRestController mealController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        springContext = new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml", "spring/spring-db.xml"}, false);
        springContext.getEnvironment().setActiveProfiles(Profiles.ACTIVE_DB, Profiles.DB_IMPLEMENTATION);
        springContext.refresh();
        mealController = springContext.getBean(MealRestController.class);
    }

    public void destroy(){
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        String id = request.getParameter("id");
        String action = request.getParameter("action");

        if(action == null){
            final Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));
            if(request.getParameter("id").isEmpty()){
                LOG.info("Create {}", meal);
                mealController.create(meal);
            }else {
                LOG.info("Update {}", meal);
                mealController.update(meal, getId(request));
            }

            response.sendRedirect("meals");
        }else if("filter".equals(action)){
            LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate   = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime   = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
            request.setAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if(action == null){
            LOG.info("getAll");
            request.setAttribute("meals", mealController.getAll());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        }else if("delete".equals(action)){
            int id = getId(request);
            LOG.info("Delete {}", id);
            mealController.delete(id);
            response.sendRedirect("meals");

        }else if("create".equals(action) || "update".equals(action)){
            final Meal meal = "create".equals(action) ?
                    new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000):
                    mealController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request){
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }




}
