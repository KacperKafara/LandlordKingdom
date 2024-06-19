package pl.lodz.p.it.ssbd2024.config;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }


    @Override
    @NonNull
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
//        MultipartConfigElement multipartConfigElement =  new MultipartConfigElement("/tmp", 1024*256, 1024*256, 0);
        registration.setMultipartConfig(new MultipartConfigElement("/tmp"));
    }
}