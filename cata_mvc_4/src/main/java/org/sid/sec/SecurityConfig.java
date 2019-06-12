package org.sid.sec;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// on va spécifier l'emplacement où sont les utilisateurs pour pouvoir les charger et effectuer l'authentificat°
		// cas où l'emplacement c'est la mémoire
		/*auth.inMemoryAuthentication().withUser("admin").password("12345").roles("USER", "ADMIN");
		auth.inMemoryAuthentication().withUser("user").password("1234").roles("USER");*/
		// cas où l'emplacement c'est la Base de données
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("SELECT username as principal, password as credentials, actived FROM users WHERE username = ?")
		        .authoritiesByUsernameQuery("SELECT username as principal, us_role as role FROM user_role WHERE username = ?")
		        .passwordEncoder(new Md5PasswordEncoder()).rolePrefix("ROLE_");
	}
    @Override
    protected void configure(HttpSecurity http) throws Exception {// ici définir les règles de sécurity
    	http.formLogin().loginPage("/login"); //si on ne spécifie pas la méthode loginPage() Spring Security afiche un formulaire par défaut même si on n'en a pas défini un.
    	//spécifier : tel URL necessite un tel Role !!!
    	http.authorizeRequests().antMatchers("/index").hasRole("USER");
    	http.authorizeRequests().antMatchers("/save", "/form", "/edit").hasRole("ADMIN"); // ou antMatchers("/admin/*") à condition de modifier RequestMapping au niveau du Controller + modifier l'adressage dans les pages.html
    	http.exceptionHandling().accessDeniedPage("/403");
    }
}
