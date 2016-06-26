package ru.stepic.webservice.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stepic.webservice.templater.PageGenerator;

@SuppressWarnings("serial")
public class Mirror extends HttpServlet {
	
	private static final Logger log = LoggerFactory.getLogger(Mirror.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("Start to handler of mirror page");
		resp.setContentType("text/html;charset=utf-8");
		resp.getWriter().println(PageGenerator.getInstance().getPage("mirror.html", 
				new HashMap<String, Object>() {{
					put("parameter", 
							req.getParameter("key") == null ? "" : req.getParameter("key"));
		}}));
		resp.setStatus(HttpServletResponse.SC_OK);
		
	}
}
