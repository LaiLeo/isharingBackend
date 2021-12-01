package com.fih.ishareing;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ServletComponentScan
@EnableScheduling
@SpringBootApplication
@EnableAsync
public class IshareingApplication implements CommandLineRunner {
	private final static Logger logger = LoggerFactory.getLogger(IshareingApplication.class);

	@Autowired(required = false)
	private BuildProperties buildProperties;

	public static void main(String[] args) {
		SpringApplication.run(IshareingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (buildProperties != null) {
			logger.info("{} service already started. version:v{}", buildProperties.getName(),
					buildProperties.getVersion());
		}
	}

//	@Bean
//	public TomcatServletWebServerFactory webServerFactory() {
//		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//		factory.addConnectorCustomizers((Connector connector) -> {
//			connector.setProperty("relaxedPathChars", "\"<>[\\]^`{|}");
//			connector.setProperty("relaxedQueryChars", "\"<>[\\]^`{|}");
//		});
//		return factory;
//	}

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
			@Override
			public void customize(Connector connector) {
//				connector.setProperty("relaxedPathChars", "\"<>[\\]^`{|}");
//				connector.setProperty("relaxedQueryChars", "\"<>[\\]^`{|}|{}[]");
				connector.setProperty("relaxedQueryChars", "{}[]");
			}
		});
		return factory;
	}

}
