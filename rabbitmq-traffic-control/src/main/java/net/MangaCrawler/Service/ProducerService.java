package net.MangaCrawler.Service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.MangaCrawler.config.RabbitMQConfig;

@Service
public class ProducerService {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	public void send(String message) {
		rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME,message);
		System.out.println("Sent: "+message);
	}
}
