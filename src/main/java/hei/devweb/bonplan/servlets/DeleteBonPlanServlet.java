package hei.devweb.bonplan.servlets;

import hei.devweb.bonplan.managers.BonPlanLibrary;
import hei.devweb.bonplan.pojos.BonPlan;
import hei.devweb.bonplan.pojos.User;
import hei.devweb.bonplan.services.BonPlanService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/deleteallbonplan")
public class DeleteBonPlanServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        BonPlanService.getInstance().deleteAllBonplan();

        resp.sendRedirect("bonPlan");
    }
}
