package ru.stepic.webservice.templater;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class PageGenerator {

	private static final String HTML_DIR = "templates";
	private final Configuration cfg;
	private static PageGenerator pageGenerator;
	
	
	private PageGenerator() {
		cfg = new Configuration();
	}

	public static PageGenerator getInstance() {
		if(pageGenerator == null) {
			pageGenerator = new PageGenerator();
		}
		return pageGenerator;
	}
	
	/**
	 * Взять страницу page.html и заменить {PARAM} значениями из карты data
	 * @param filename
	 * @param data
	 * @return
	 */
	public String getPage(final String filename, Map<String, Object> data) {
		Writer stream = new StringWriter();
		try {
			Template template = cfg.getTemplate(HTML_DIR + File.separator + filename, Locale.getDefault());
			template.process(data, stream);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		return stream.toString();
	}
	
//	public String getPage(final String filename, String data) {
//		try {
//			Template template = cfg.getTemplate(HTML_DIR + File.separator + filename, Locale.getDefault());
//			template.process(data, stream);
//		} catch (IOException | TemplateException e) {
//			e.printStackTrace();
//		}
//		return stream.toString();
//	}
	
	
}
