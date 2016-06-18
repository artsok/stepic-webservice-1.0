package ru.stepic.webservice;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ru.stepic.webservice.servlets.Frontend;

/**
 * 
 * @author Artem Sokovets
 *
 */
public class StartServer {
	public static void main(String[] args) throws Exception {
		Frontend allRequests = new Frontend();
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.addServlet(new ServletHolder(allRequests), "/*"); //Слушаем любой ресурс
		
		Server server = new Server(8080); //сам Jetty
		server.setHandler(context);
		
		server.start();
		server.join();
	}
}
