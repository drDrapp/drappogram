package ru.drdrapp.drappogram.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.Principal;

@Configuration
public class FreeMarkerConfig implements WebMvcConfigurer {

//    private final MyVariables myVariables;
//
//    public FreeMarkerConfig(MyVariables myVariables) {
//        this.myVariables = myVariables;
//    }
//
//    @Bean
//    public FreeMarkerConfigurer freeMarkerConfigurer(){
//        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("myvars", myVariables.getData());
//        configurer.setFreemarkerVariables(variables);
//        return configurer;
//    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.freeMarker();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
                                    @Override
                                    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                                        if (modelAndView != null) {
                                            Principal principal = request.getUserPrincipal();
                                            if (principal != null) {
                                                String dgUserName = principal.getName();
                                                if (!dgUserName.isEmpty()) {
                                                    modelAndView.addObject("dgUserName", dgUserName);
                                                    modelAndView.addObject("dgUserRoles", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                                                }
                                            }
                                        }
                                    }
                                }
        );
    }
}