package lu.mypost.mep.configuration;

import com.fasterxml.classmate.TypeResolver;
import lu.mypost.mep.model.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Autowired
    private TypeResolver resolver;

    @Autowired
    private Environment env;

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("lu.mypost.mep.controller"))
                .paths(PathSelectors.any())
                .build()
                .host("mep.bober.ovh/api")
                .apiInfo(metaData())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, getListOfErrorsMessage())
                .globalResponseMessage(RequestMethod.PUT, getListOfErrorsMessage())
                .globalResponseMessage(RequestMethod.POST, getListOfErrorsMessage())
                .globalResponseMessage(RequestMethod.PATCH, getListOfErrorsMessage())
                .additionalModels(resolver.resolve(ErrorResponse.class));

    }

    private List<ResponseMessage> getListOfErrorsMessage() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(500)
                        .message("INTERNAL_ERROR")
                        .responseModel(new ModelRef("ErrorResponse"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(400)
                        .message("BAD_REQUEST")
                        .responseModel(new ModelRef("ErrorResponse"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(404)
                        .message("NOT_FOUND")
                        .responseModel(new ModelRef("ErrorResponse"))
                        .build()
        );
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title(this.env.getProperty("projectName"))
                .description(this.env.getProperty("description"))
                .version(this.env.getProperty("version"))
                .license(this.env.getProperty("license"))
                .licenseUrl(this.env.getProperty("licenseUrl"))
                .build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
