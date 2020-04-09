package ru.itis.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ru.itis.service.ViewResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class ViewResolverImpl implements ViewResolver {
    @Autowired
    @Qualifier(value = "getConfig")
    private Configuration configuration;

    @Override
    public void process(String name, Map<String, Object> root, HttpServletResponse response) {
        try {
            Template t = configuration.getTemplate(name);
            response.setContentType("text/html;charset=utf-8");
            t.process(root, response.getWriter());
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String process(String name, Map<String, Object> root) {
        try {
            Template t = configuration.getTemplate(name);
            return FreeMarkerTemplateUtils.processTemplateIntoString(t, root);
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
    }
}
