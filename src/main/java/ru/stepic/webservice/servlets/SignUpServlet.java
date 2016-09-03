package ru.stepic.webservice.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stepic.webservice.accounts.AccountService;
import ru.stepic.webservice.entity.UserProfile;

@SuppressWarnings("serial")
public class SignUpServlet extends HttpServlet {
	
	private final AccountService accountService;
	
	private static final Logger log = LoggerFactory.getLogger(SignUpServlet.class);

	public SignUpServlet(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Start to handler of SignUp page");
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        
        if (login == null || pass == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        accountService.addNewUser(new UserProfile(login, DigestUtils.md2Hex(pass)));
        log.info("Login: '" + login + "' registered.");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
	}
	
}
