package lk.shaanzx.online_auction_system_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
/*    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/items/**")
                .allowedOrigins("*")  // Frontend (React) origin එක allow කරනවා
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:C:\\Users\\shaanzx\\IdeaProjects\\Online_Auction_System_FrontEnd\\src\\assets\\images\\");
    }*/
}
