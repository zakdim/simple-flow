package sia5;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SimpleFlowApplication {
	
	public static final String FILE_NAME = "simple.txt";

	public static void main(String[] args) {
		SpringApplication.run(SimpleFlowApplication.class, args);
	}

	// run from command line with profile:
	// mvn spring-boot:run --Dspring-boot.run.profiles=xmlconfig
	@Bean
	public CommandLineRunner writeData(FileWriterGateway gateway, Environment env) {
		return args -> {
			String[] activeProfiles = env.getActiveProfiles();
			if (activeProfiles.length > 0) {
				String profile = activeProfiles[0];
				String msg = "Hello, Spring Integration! (" + profile + ")";
				log.info(String.format("writing message '%s' to file %s via spring integration flow...",
						msg, FILE_NAME));
				gateway.writeToFile(FILE_NAME, msg);
			} else {
				log.info(
						"No active profile set. Should set active profile to one of xmlconfig, javaconfig, or javadsl.");
			}
		};
	}

}
