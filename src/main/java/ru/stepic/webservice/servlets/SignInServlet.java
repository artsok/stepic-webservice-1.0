package ru.stepic.webservice.servlets;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stepic.webservice.accounts.AccountService;
import ru.stepic.webservice.accounts.UserProfile;

@SuppressWarnings("serial")
public class SignInServlet extends HttpServlet {

	private final AccountService accountService;
	
	private static final Logger log = LoggerFactory.getLogger(SignInServlet.class);

	public SignInServlet(AccountService accountService) {
		this.accountService = accountService;
	}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.info("Start to handler of signIn page");
    	response.setContentType("text/html;charset=utf-8");
    	
    	String login = request.getParameter("login");
        String pass = request.getParameter("password");
        String sessionId = request.getSession().getId();
        
        if (login == null || pass == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        } 
    
        if(Optional.ofNullable(accountService.getUserByLogin(login)).isPresent()) {
        	if(accountService.getSessionIdToProfile().containsKey(sessionId)) {
        		response.getWriter().println("You already login.");
        		response.setStatus(HttpServletResponse.SC_OK);       
        		return;
        	}
        	accountService.addSession(request.getSession().getId(), new UserProfile(login, pass));
        	response.getWriter().println("Authorized: " + login);
        	response.setStatus(HttpServletResponse.SC_OK);        	
        } else {
        	response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
