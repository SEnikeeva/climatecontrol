package ru.itis.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.itis.security.filter.JwtAuthenticationFilter;
import ru.itis.security.handlers.AccessDeniedHandler;
import ru.itis.security.provider.JwtAuthenticationProvider;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier(value = "provider")
    private JwtAuthenticationProvider authenticationProvider;

//    public AppSecurityConfig(JwtAuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.anonymous().principal("guest").authorities("GUEST_ROLE");
        http.csrf().disable();
        http.formLogin().disable();

        http.logout().disable();
                /*.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("SESSION", "remember-me", "cookieName")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login?logout");*/
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterAt(tokenProcessingFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new AnonymousAuthenticationFilter("twrt454"), JwtAuthenticationFilter.class);
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, AnonymousAuthenticationFilter.class);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new AccessDeniedHandler("error");
    }
    @Bean
    public JwtAuthenticationFilter tokenProcessingFilter() throws Exception {
        JwtAuthenticationFilter tokenProcessingFilter = new JwtAuthenticationFilter();
        tokenProcessingFilter.setAuthenticationManager(authenticationManager());
        return tokenProcessingFilter;
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider).authenticationProvider(new AnonymousAuthenticationProvider("twrt454"));
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


}