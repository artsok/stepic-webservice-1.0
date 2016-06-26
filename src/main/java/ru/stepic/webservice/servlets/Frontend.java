package ru.stepic.webservice.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.stepic.webservice.templater.PageGenerator;

/**
 * 
 * @author Artem Sokovets (relix@mail.ru)
 * 
 */
@SuppressWarnings("serial")
public class Frontend extends HttpServlet {
	
	private static final Logger log = LoggerFactory.getLogger(Frontend.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> pageVariables = createPageVariablesMap(req);
		pageVariables.put("message", "");
		resp.getWriter().println(PageGenerator.getInstance().getPage("page.html", pageVariables));
		resp.setContentType("text/html;charset=utf-8");
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * Отображаем параметры request 
	 * @param req
	 * @return
	 */
	private static Map<String, Object> createPageVariablesMap(final HttpServletRequest req) {
		Map<String, Object> pageVariables = new HashMap<String, Object>() {{
			put("method", req.getMethod());
			put("URL", req.getRequestURL());
			put("pathInfo", req.getPathInfo() == null ? "" : req.getPathInfo());
			put("sessionId", req.getSession().getId());
			put("parameters", req.getParameterMap().toString());
		}};
		return pageVariables;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> pageVariables = createPageVariablesMap(req);
		String message = req.getParameter("message"); //Параметр из метода post
		if(message == null || message.isEmpty()) {
			resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
		} else {
			resp.setStatus(HttpServletResponse.SC_OK);
		}
		pageVariables.put("message", message == null ? "" : message);
		resp.getWriter().println(PageGenerator.getInstance().getPage("page.html", pageVariables));
		resp.setContentType("text/html;charset=utf-8");
	}
}
