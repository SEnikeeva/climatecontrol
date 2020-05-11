package ru.itis.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface ViewResolver {
    void process(String name, Map<String, Object> root, HttpServletResponse response);
    String process(String name, Map<String, Object> root);
}
