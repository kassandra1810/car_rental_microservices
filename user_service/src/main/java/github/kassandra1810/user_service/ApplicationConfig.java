package github.kassandra1810.user_service;

import github.kassandra1810.user_service.user.UserService;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

// tomek

@Configuration
public class ApplicationConfig {

    @Bean
    public UserService getUserService(Source messageSource) {
        return new UserService(messageSource);
    }

    @Bean
    public Docket get(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //.paths(PathSelectors.ant("/api/**"))  //only /api/ endpoints will be documented
                .apis(RequestHandlerSelectors.basePackage("github.kassandra1810.user_service.user")) //only api from this package will be documented
                .build().apiInfo(createApiInfo());
    }



    private ApiInfo createApiInfo() {
        return new ApiInfo("Users API",
                "User information",
                "1.00",
                "https://liliiabaluk.pl/",
                new Contact("Liliia", "https://liliiabaluk.pl/", "liliabaluk@gmail.com"),
                "my own license",
                "https://liliiabaluk.pl/",
                Collections.emptyList()
        );
    }
}
