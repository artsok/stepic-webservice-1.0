package ru.stepic.webservice;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stepic.webservice.servlets.Frontend;
import ru.stepic.webservice.servlets.Mirror;

/**
 * 
 * @author Artem Sokovets
 *
 */
public class StartServer {
	
	private static final Logger log = LoggerFactory.getLogger(StartServer.class);
	
	public static void main(String[] args) throws Exception {	
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.addServlet(new ServletHolder(new Frontend()), "/*"); 
		context.addServlet(new ServletHolder(new Mirror()), "/mirror");
		
		Server server = new Server(8080); //сам Jetty
		server.setHandler(context);
		server.start();
		log.info("Server started");
		server.join();
	}
}
