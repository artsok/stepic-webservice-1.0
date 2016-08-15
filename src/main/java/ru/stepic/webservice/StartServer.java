package ru.stepic.webservice;


import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stepic.webservice.accounts.AccountService;
import ru.stepic.webservice.accounts.UserProfile;
import ru.stepic.webservice.dbservice.DbService;
import ru.stepic.webservice.dbservice.dao.UsersDAO;
import ru.stepic.webservice.dbservice.datasets.UsersDataSet;
import ru.stepic.webservice.servlets.Frontend;
import ru.stepic.webservice.servlets.Mirror;
import ru.stepic.webservice.servlets.SessionsServlet;
import ru.stepic.webservice.servlets.SignInServlet;
import ru.stepic.webservice.servlets.SignUpServlet;
import ru.stepic.webservice.servlets.UsersServlet;

/**
 * 
 * @author Artem Sokovets
 *
 */
public class StartServer {
	
	private static final Logger log = LoggerFactory.getLogger(StartServer.class);
	
	public static void main(String[] args) throws Exception {
		AccountService accountService = new AccountService();
		
		//При обращение к ресурсу, вызывается переопределенный метод (указываем в какой директории искать ресурсы)
		ResourceHandler resource_handler = new ResourceHandler() {
            @Override
            public Resource getResource(String path) throws MalformedURLException {
                Resource resource = Resource.newClassPathResource(path);
                log.info("CALL ME - " + path);
                if (resource == null || !resource.exists()) {
                	log.info("CALL ME - null - "+ path);
                    resource = Resource.newClassPathResource("/public_html");
//                   
//                    try {
//                    	log.info("debug");
//                    	if(resource != null) {
//                    		log.info(resource.getFile().toPath().toString());
//                    	}
//
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//					}
                    
                }
                return resource;
            }
        };
		
		//ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true); // Разрешим просмотр списка файлов в папках
        
        //resource_handler.setResourceBase("/public_html"); // Установим базовой директорию ./web
		resource_handler.setWelcomeFiles(new String[]{"index.html"}); // В качестве главной страницы будет использоваться index.html
		//resource_handler.setResourceBase("public_html");
		resource_handler.setResourceBase(".");
		
		 

	
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		  log.info("Init out1");
		context.addServlet(new ServletHolder(new Frontend()), "/page"); 
		context.addServlet(new ServletHolder(new Mirror()), "/mirror");
//		context.addServlet(new ServletHolder(new UsersServlet(accountService)), "/api/v1/users");
//		context.addServlet(new ServletHolder(new SessionsServlet(accountService)), "/api/v1/sessions");
		context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
		context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");
		

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context}); 
		
        Server server = new Server(8080);
        server.setHandler(handlers);
		
		server.start();
		log.info("Server started");
		server.join();
		
		//DbService db = DbService.getInstance();
//		db.printConnectInfo();
//		long userId = db.addUser("Artem");
//		log.info("Added user id: " + userId);
//		
//		UsersDataSet dataSet = db.getUser(userId);
//		log.info("User data set:" + dataSet);
//		
//		db.cleanUp();
	}
}
