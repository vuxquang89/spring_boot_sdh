package vux.codejava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

@SpringBootApplication
//@Configuration
//@EnableAutoConfiguration
@ComponentScan("vux.codejava")
//@EnableJpaRepositories("vux.codejava.repository")
public class DeviceManagerApplication extends SpringBootServletInitializer {

	@Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DeviceManagerApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DeviceManagerApplication.class, args);
	}

}
