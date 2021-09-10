# Technology-Assessment
letshego Technology-Assessment

On a retail website, the following discounts apply:
1. If the user is an employee of the store, he gets a 30% discount
2. If the user is an affiliate of the store, he gets a 10% discount
3. If the user has been a customer for over 2 years, he gets a 5% discount.
4. For every $100 on the bill, there would be a $ 5 discount (e.g. for $ 990, you get $ 45
as a discount).
5. The percentage based discounts do not apply on groceries.
6. A user can get only one of the percentage based discounts on a bill.


There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `RetailSiteApplication` class from your IDE.or one can run the app from command line
```shell
mvn spring-boot:run
```

### Build an executable JAR
You can run the application from the command line using:
```
mvn spring-boot:run


The solution exposes one rest end point - post method, which accepts the following structure for payment information:
```
{
	"userInfo": {
		"type": enumeration,
		"data": object
	},
	"amount": number,
	"otherData": object,
	"containsGroceries": boolean
}
- The `amount` property is the total bill.

- The `userInfo` property can be extended to keep more data, but its key data is the `type` property, which can be the following enumerations [`EMPLOYEE`, `AFFILIATE`, `LOYAL`, `REGULAR`]. If this property is not specified it is assigned as `REGULAR`.
    The `EMPLOYEE` enum describes a user that is an employee of the store.
    The `AFFILIATE` enum describes a user that is affiliate of the store.
    The `LOYAL` enum describes a user that has been a customer for over 2 years.
    The `REGULAR` enum describes a user type, to which the percentage discount does not applies.

- The `containsGroceries` tells whether the purchased stocks include groceries.

The returned response has the following structure:
```
{
    "discount": number,
    "totalBill": number,
    "type": enumeration
}
```

The `discount` property is the total amount of the discount, the `totalBill` is the initial bill and the type corresponds to the discount type applied.
There are 4 options:

    EMPLOYEE_DISCOUNT - discount for employees;

    AFFILIATE_DISCOUNT - discount for affiliate user;

    LOYALTY_DISCOUNT - discount for user that has been customer for more that 2 years;

    NONE - no discount is applied.

To every payment an additional "voucher based" is applied, which corresponds to point 4. of the requirements.

Example request and response:
    - Request:
```
    {
        "userInfo": {
            "type": "AFFILIATE",
            "data": "affiliate user example"
        },
        "amount": 323.44,
        "otherData": "request example",
        "containsGroceries": false
    }
```
    - Response:
```
    {
        "discount": 47.344,
        "totalBill": 323.44,
        "type": "AFFILIATE_DISCOUNT"
    }
```
Run `mvn test` to run tests and generate code coverage report

There are several test classes, which simply assert that the services work correctly. There is one "integration" test for the rest service, which mocks http request and asserts that responses. Some of the test classes share the same test logic, so a dedicated parent class is defined for them.
There are no test cases for the data classes, because they are simply getters and setters.
 
 for documentation I will intergrate the Swagger UI to expose the endpoints in our rest controller 


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("controllers"))
                .paths(regex("/api/discount.*"))
                .build()
                .apiInfo(metaData());
    }
    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for retail",
                "1.0",
                "Terms of service",
                new Contact("Amo",  "amogelangmakaba@gmail.com"),
               "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }
}

