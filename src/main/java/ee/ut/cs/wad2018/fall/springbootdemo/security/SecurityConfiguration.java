package ee.ut.cs.wad2018.fall.springbootdemo.security;

import ee.ut.cs.wad2018.fall.springbootdemo.security.smartid.SmartIdAuthenticationProcessingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // disable cors() and csrf() for localhost development
        http.cors().and().csrf().disable();

        http.addFilterAfter(smartIdAuthenticationFilter(), RequestHeaderAuthenticationFilter.class);

        // by default allow requests to any URL
        http.authorizeRequests()
                .antMatchers("/secured/**").authenticated()
                .antMatchers("/**").permitAll();

        // set default login page redirect
        http.formLogin().loginPage("/smart-id/login");
    }

    @Bean
    public SmartIdAuthenticationProcessingFilter smartIdAuthenticationFilter() throws Exception {
        SmartIdAuthenticationProcessingFilter filter = new SmartIdAuthenticationProcessingFilter(
                new AntPathRequestMatcher("/smart-id/login/finalize", "POST")
        );
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // need to override this as empty, to expose AuthenticationManagerBean
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
