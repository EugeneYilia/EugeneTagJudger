package EugeneContainer.catalina;

import EugeneContainer.config.ServletConfig;
import EugeneContainer.config.ServletConfigContext;
import EugeneContainer.server.RequestFacade;
import EugeneContainer.server.ResponseFacade;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Container {
    public void invoke(RequestFacade requestFacade, ResponseFacade responseFacade) {
        String requestURI = requestFacade.getRequestURI();
        ArrayList<ServletConfig> servletConfigs = ServletConfigContext.getServletConfigArrayList();
        for (ServletConfig servletConfig : servletConfigs) {
            if (servletConfig.getMappingUri().equals(requestURI)) {
                try {
                    Class clazz = Class.forName("webroot/servlet/" + servletConfig.getClassName());
                    Servlet servlet = (Servlet) clazz.newInstance();
                    servlet.service(requestFacade, responseFacade);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        try {
            responseFacade.sendError(404);
            PrintWriter printWriter = responseFacade.getWriter();
            printWriter.println("The corresponding servlet doesn't exist");
            printWriter.flush();
            printWriter.close();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
