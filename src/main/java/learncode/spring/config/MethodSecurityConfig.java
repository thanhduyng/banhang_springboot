package learncode.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import learncode.spring.service.CustomPermissionEvaluator;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

	@Bean
	public CustomPermissionEvaluator getCusstomPermission() {
		return new CustomPermissionEvaluator();
	}
	
//	@Bean
//	public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
//	    return new BufferedImageHttpMessageConverter();
//	}
	
	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		DefaultMethodSecurityExpressionHandler expressionHandler = 
				new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator(this.getCusstomPermission());
		return expressionHandler;
	}

	
}
