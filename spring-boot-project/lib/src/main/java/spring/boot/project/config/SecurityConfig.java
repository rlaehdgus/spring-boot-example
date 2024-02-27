package spring.boot.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtTokenProvider jwtTokenProvider;
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.httpBasic((httpBasicConfig) ->
				// rest api 이므로 기본설정 안함.(기본 설정은 비인증 시 로그인페이지로 리다이렉트 처리)
				httpBasicConfig.disable()
			)
			.csrf((csrfConfig) ->
				// rest api 이므로 csrf 보안 사용 안함
				csrfConfig.disable()
			)
			.sessionManagement((sessionMngConfig) ->
				// jwt token을 사용하므로 세션 사용 안함 
				sessionMngConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.headers((headerConfig) ->
				headerConfig.frameOptions(frameOptionsConfig ->
					frameOptionsConfig.disable()
				)
			)
			.authorizeHttpRequests((authorizeRequests) ->
				authorizeRequests
//					.requestMatchers(PathRequest.toH2Console()).permitAll()
					.requestMatchers("/", "/login/**", "/swagger-ui/**").permitAll()
//					.requestMatchers("/posts/**", "/api/v1/posts/**").hasRole(Role.USER.name())
//					.requestMatchers("/admins/**", "/api/v1/admins/**").hasRole(Role.ADMIN.name())
//					.anyRequest().authenticated()
			)
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
//			.exceptionHandling((exceptionConfig) ->
//				exceptionConfig.authenticationEntryPoint(unauthorizedEntryPoint).accessDeniedHandler(accessDeniedHandler)
//			) 
//			.formLogin((formLogin) ->
//				formLogin
//					.loginPage("/login/login") 
//					.usernameParameter("username") 
//					.passwordParameter("password") 
//					.loginProcessingUrl("/login/login-proc") 
//					.defaultSuccessUrl("/", true)
//			)
//			.logout((logoutConfig) ->
//				logoutConfig.logoutSuccessUrl("/") 
//			)
//			.userDetailsService(null); 
			;
	
	    return http.build();
	}
	
    @Bean
    public PasswordEncoder PasswordEncoder() {
    	//return new MessageDigestPasswordEncoder("SHA-256");
    	return new BCryptPasswordEncoder();
    }
}