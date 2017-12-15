package hei.devweb.bonplan.servlets;

import hei.devweb.bonplan.managers.BonPlanLibrary;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class BonPlanDeletionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer idBP = Integer.parseInt(req.getParameter("idBP"));
        String confirm = req.getParameter("confirm");
        if(confirm != null && Boolean.parseBoolean(confirm)){
            BonPlanLibrary.getInstance().deleteBonPlan(idBP);
            resp.sendRedirect("/bonPlan");
            return;
        }

        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(req.getServletContext());
        templateResolver.setPrefix("WEB-INF/templates/");
        templateResolver.setSuffix(".html"+"");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("bonplan", BonPlanLibrary.getInstance().getBonPlan(idBP));

        templateEngine.process("bonPlandeletion", context, resp.getWriter());
    }
}
