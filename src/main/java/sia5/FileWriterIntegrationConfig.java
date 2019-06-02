package sia5;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageChannel;

@Configuration
public class FileWriterIntegrationConfig {

	@Profile("xmlconfig")
	@Configuration
	@ImportResource("classpath:/filewriter-config.xml")
	public static class XmlConfiguration {}
	
	@Profile("javaconfig")
	@Bean
	@Transformer(inputChannel = "textInChannel",
				 outputChannel = "fileWriterChannel")
	public GenericTransformer<String, String> upperCaseTransformer() {
		return text -> text.toUpperCase();
	}
	
	@Profile("javaconfig")
	@Bean
	@ServiceActivator(inputChannel = "fileWriterChannel")
	public FileWritingMessageHandler fileWriter() {
		FileWritingMessageHandler handler = 
				new FileWritingMessageHandler(new File("/tmp/sia5/files"));
		handler.setExpectReply(false);
		handler.setFileExistsMode(FileExistsMode.APPEND);
		handler.setAppendNewLine(true);
		
		return handler;
	}
	
//	@Bean
//	public MessageChannel textInChannel() {
//		return new DirectChannel();
//	}
//	
//	@Bean
//	public MessageChannel fileWriterChannel() {
//		return new DirectChannel();
//	}
	
	
}
