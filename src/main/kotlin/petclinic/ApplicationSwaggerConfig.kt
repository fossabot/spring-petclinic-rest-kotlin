/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package petclinic

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.service.VendorExtension
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * Java config for Springfox swagger documentation plugin
 *
 * @author Vitaliy Fedoriv
 */

@Configuration
@EnableSwagger2
class ApplicationSwaggerConfig {

    private val apiInfo: ApiInfo
        get() = ApiInfo(
            "REST Petclinic backend Api Documentation",
            "This is REST API documentation of the Spring Petclinic backend",
            "1.0",
            "Petclinic backend terms of service",

            Contact(
                "Vitaliy Fedoriv",
                "https://github.com/spring-petclinic/spring-petclinic-rest",
                "vitaliy.fedoriv@gmail.com"
            ),
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            emptyList<VendorExtension<*>>()
        )

    @Bean
    open fun customDocket(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo)
    }
}