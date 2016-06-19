package ru.stepic.webservice.templater;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class PageGenerator {
	private final Configuration cfg;
	private static PageGenerator pageGenerator;
	private static final Logger log = LoggerFactory.getLogger(PageGenerator.class);
	
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
			ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(), "/templates"); //Для загрузки шаблонов из jar
			cfg.setTemplateLoader(ctl);
			Template template = cfg.getTemplate(filename);		
			template.process(data, stream);
		} catch (IOException | TemplateException e) {
			log.error(e.getMessage());
		}
		return stream.toString();
	}	
}
