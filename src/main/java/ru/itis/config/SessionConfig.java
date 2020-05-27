package ru.itis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.FlushMode;
import org.springframework.session.SaveMode;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Configuration
@EnableJdbcHttpSession(saveMode = SaveMode.ALWAYS, flushMode = FlushMode.IMMEDIATE)
public class SessionConfig extends AbstractHttpSessionApplicationInitializer {
}
