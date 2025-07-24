package net.MangaCrawler.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String QUEUE_NAME = "traffic_queue";
	
	@Bean
	public Queue trafficQueue() {
		return new Queue(QUEUE_NAME, true);
	}
	
}
