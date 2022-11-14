package br.com.alison.aws.awsdynamodb.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "br.com.alison.aws.awsdynamodb")
public class AwsDemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
		SpringApplication.run(AwsDemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Instant now = Instant.now();
		OffsetDateTime offsetDateTimeNow = OffsetDateTime.now();
		log.info("---> Started on " + ZonedDateTime.ofInstant(now, ZoneId.systemDefault()).toOffsetDateTime());
		log.info("Default Timezone: " + ZoneId.systemDefault() + " | OffsetDateTime:" + OffsetDateTime.now());
		log.info("UTC Time: " + ZonedDateTime.ofInstant(now, ZoneId.of("UTC")));
		log.info("São Paulo: " + ZonedDateTime.ofInstant(now, ZoneId.of("America/Sao_Paulo")));
		log.info("OffsetDateTime: " + offsetDateTimeNow);
		log.info("Java-Name: {}", System.getProperty("java.specification.name"));
		log.info("Java-Vendor: {}", System.getProperty("java.specification.vendor"));
		log.info("Java-Version: {}", System.getProperty("java.specification.version"));
		log.info("Java-Runtime-Version: {}", System.getProperty("java.runtime.version"));
		log.info("file.encoding: {}", System.getProperty("file.encoding"));
	}

	@Bean
	ApplicationRunner applicationRunner(@Value("${password1}") String password1,
										@Value("${password2}") String password2,
										@Value("${parameterstore1}") String parameterstore1) {
		return args -> {
			log.info("`password` loaded from the AWS Secret Manager: {} {}", password1, password2);
			log.info("`ssm parameter store` loaded from the AWS SSM: {} ", parameterstore1);
		};
	}
}
