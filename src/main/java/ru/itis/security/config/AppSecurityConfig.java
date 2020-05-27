package ru.itis.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.GenericFilterBean;
import ru.itis.security.filter.JwtAuthenticationFilter;
import ru.itis.security.handlers.AccessDeniedHandler;
import ru.itis.security.provider.JwtAuthenticationProvider;

import javax.sql.DataSource;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "ru.itis")
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Configuration
    @Order(1)
    public static class RestConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired
        @Qualifier(value = "provider")
        private JwtAuthenticationProvider authenticationProvider;


        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**");
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
       /*     http.addFilterBefore(new AnonymousAuthenticationFilter("twrt454"), JwtAuthenticationFilter.class);
            CharacterEncodingFilter filter = new CharacterEncodingFilter();
            filter.setEncoding("UTF-8");
            filter.setForceEncoding(true);
            http.addFilterBefore(filter, AnonymousAuthenticationFilter.class);*/
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


    }

    @Configuration
    @Order(2)
    public static class WebSecurityConfig1 extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier(value = "provider")
        private JwtAuthenticationProvider authenticationProvider;


        @Override
        public void configure(WebSecurity web) throws Exception {
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/**");
            http.authorizeRequests().antMatchers("/profile").authenticated();
            http.anonymous().principal("guest").authorities("GUEST_ROLE");
            http.rememberMe().rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository());
        http.csrf().disable();
            http.formLogin().disable();
       /*     http.formLogin()
                    .loginPage("/signIn")
                    .defaultSuccessUrl("/profile", true)
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .failureUrl("/signIn?error=true")
                    .and()
                    .rememberMe().rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository());
*/
            http.logout()
//                    .disable();
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("SESSION", "remember-me", "cookieName")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/signIn?logout");
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterAt(tokenProcessingFilter(), BasicAuthenticationFilter.class);
            http.addFilterBefore(new AnonymousAuthenticationFilter("twrt454"), JwtAuthenticationFilter.class);
            CharacterEncodingFilter filter = new CharacterEncodingFilter();
            filter.setEncoding("UTF-8");
            filter.setForceEncoding(true);
            http.addFilterBefore(filter, AnonymousAuthenticationFilter.class);
        }

        @Bean
        public AccessDeniedHandler accessDeniedHandler() {
            return new AccessDeniedHandler("error");
        }

        @Bean
        public JwtAuthenticationFilter tokenProcessingFilter() throws Exception {
            JwtAuthenticationFilter tokenProcessingFilter = new JwtAuthenticationFilter();
            tokenProcessingFilter.setAuthenticationManager(authenticationManager());
            System.out.println(tokenProcessingFilter);;
            return tokenProcessingFilter;
        }

        @Autowired
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider).authenticationProvider(new AnonymousAuthenticationProvider("twrt454"));
        }
        @Autowired
        private DataSource dataSource;

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
            jdbcTokenRepository.setDataSource(dataSource);
            return jdbcTokenRepository;
        }



    }
}