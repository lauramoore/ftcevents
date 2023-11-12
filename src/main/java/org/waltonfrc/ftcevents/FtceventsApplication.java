package org.waltonfrc.ftcevents;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FtceventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtceventsApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(FtcEventService eventService) {
		return args -> {

			List<Match> matches = eventService.getListOfMatches();
			System.out.println(matches);

		};
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}


}
