package br.edu.fatec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import br.edu.fatec.jwt.JwtAuthenticationFilter;
import br.edu.fatec.jwt.JwtUtils;
import br.edu.fatec.jwt.RestAuthenticationEntryPoint;
import br.edu.fatec.service.JwtUserDetailsService;

@Configuration("MySecurityConfig")
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RestAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Value("Authorization")
	private String tokenHeader;

	@Value("/auth")
	private String authenticationPath;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoderBean());
	}

	@Bean
	public PasswordEncoder passwordEncoderBean() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	@Bean
	public JwtUtils jwtUtils() {
		return new JwtUtils();
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new AuthenticationManager() {
			
			@Override
			public Authentication authenticate(Authentication arg0) throws AuthenticationException {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}


	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	  @Override
	    protected void configure(HttpSecurity httpSecurity) throws Exception {
	        httpSecurity
	            // we don't need CSRF because our token is invulnerable
	            .csrf().disable()

	            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

	            // don't create session
	            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()

	            .authorizeRequests()

	            // Un-secure mysql Database
	            .antMatchers("/login").permitAll()
	            .anyRequest().authenticated();

	        // Custom JWT based security filter
	        JwtAuthenticationFilter authenticationTokenFilter = new JwtAuthenticationFilter();
	        httpSecurity
	            .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
	            
	        // disable page caching
	        httpSecurity
	            .headers()
	            .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
	            .cacheControl();
	    }


	// public void addCorsMappings(CorsRegistry registry) {
	// registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedHeaders("Authorization")
	// .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE",
	// "CONNECT");
	// }

}
