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

@WebServlet("/bonPlan")
public class BonPlanListServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(req.getServletContext());
        templateResolver.setPrefix("WEB-INF/templates/");
        templateResolver.setSuffix(".html"+"");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<BonPlan> bonPlans = BonPlanLibrary.getInstance().listBonPlans();
        context.setVariable("bonplans", bonPlans);

        templateEngine.process("bonPlan", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //GET PARAMETERS

        String nomBP = req.getParameter("nomEvent");
        String descriptifBP = req.getParameter("descriEvent");
        String lieuBP = req.getParameter("lieu");
        Integer prixBP = Integer.parseInt(req.getParameter("prix"));

        LocalDate dateBP ;
        String dateBPAsString = req.getParameter("date");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateBP = LocalDate.parse(dateBPAsString, dateFormat);
        String nomUser = req.getParameter("nomUser");

        BonPlanLibrary.getInstance().addUser(nomUser);

        User user = BonPlanLibrary.getInstance().getUser(nomUser);

        //CREATE BONPLAN

        BonPlan bonPlan = new BonPlan(null, nomBP, descriptifBP, lieuBP, prixBP , dateBP, user);

        BonPlanLibrary.getInstance().addBonPlan(bonPlan);

        resp.sendRedirect("bonPlan");
    }
}
