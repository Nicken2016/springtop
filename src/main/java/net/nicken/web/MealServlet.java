package net.nicken.web;

import net.nicken.AuthorizedUser;
import net.nicken.model.Meal;
import net.nicken.repository.MealRepository;
import net.nicken.repository.mock.InMemoryMealRepositoryImpl;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet{
private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

//    private MealRepository repository;

    private ConfigurableApplicationContext springContext;
    private MealRestController mealController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealController = springContext.getBean(MealRestController.class);
    }

    public void destroy(){
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        final Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if(meal.isNew()){
            LOG.info("Create {}", meal);
            mealController.create(meal);
        }else {
            LOG.info("Update {}", meal);
            mealController.update(meal, getId(request));
        }
        response.sendRedirect("meals");
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
            final Meal meal = action.equals("create") ?
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
