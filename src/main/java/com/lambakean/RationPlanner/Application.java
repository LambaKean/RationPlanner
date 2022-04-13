package com.lambakean.RationPlanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collection;
import java.util.Collections;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean("bCryptPasswordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lambakean"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        String title = "Ration Planner API";
        String desciption = "Для корректного выполнения некоторых запросов приложение должно аутентифицировать" +
                " пользователя. \n Для успешной аутентификации пользователя в запросе должен пристуствовать " +
                "заголовок Authorization со значением \"Bearer [access token]\". Access токен можно получить при" +
                " помощи некоторых методов в контроллере user-controller.";
        String version = "1.0";
        Contact contact = new Contact(null, null, "lambakean@gmail.com");
        String license = "Apache 2.0";
        String licenseUrl = "https://www.apache.org/licenses/LICENSE-2.0";
        Collection<VendorExtension> vendorExtensions = Collections.emptyList();

        return new ApiInfo(
                title,
                desciption,
                version,
                null,
                contact,
                license,
                licenseUrl,
                vendorExtensions
        );
    }
}
