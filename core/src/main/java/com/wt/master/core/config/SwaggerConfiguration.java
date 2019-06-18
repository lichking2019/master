package com.wt.master.core.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 *
 * @author lichking2019@aliyun.com
 * @date Apr 29, 2019 at 1:42:56 PM
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "com.wt.framework.config.swagger", value = "enable")
@Slf4j
public class SwaggerConfiguration {
    public SwaggerConfiguration() {
        log.info(">>>>>[系统配置]，发现应用配置了swagger[{}]，进行swagger配置", "com.wt.framework.config.swagger.enable=true");
    }

    /**
     * 定义分隔符,配置Swagger多包
     */
    private static final String splitor = ";";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(basePackage("com.aptech" + splitor + "com.wt")).paths(PathSelectors.any()).build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("[效能分析]服务端RestAPI接口描述").description("spring boot , swagger2").termsOfServiceUrl("http:github.com/").contact("lichking2019@aliyun.com").version("1.0").build();
    }

    /**
     * 重写basePackage方法，使能够实现多包访问，复制贴上去
     *
     * @param basePackage 扫描包
     * @return
     */
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    /**
     * @param basePackage
     * @return
     */
    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
}