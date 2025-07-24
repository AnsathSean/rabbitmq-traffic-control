package net.MangaCrawler.Service;


import com.rabbitmq.client.*;

import net.MangaCrawler.config.RabbitMQConfig;

import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DynamicConsumer {

	@Autowired
	private ConnectionFactory connectionFactory;
	
	private final ExecutorService executor = Executors.newCachedThreadPool();
	private final AtomicInteger consumerCount = new AtomicInteger(0);
	
    public void addNewConsumer() {
        executor.submit(() -> {
            int id = consumerCount.incrementAndGet();
            try (Connection conn = connectionFactory.createConnection();
                 Channel channel = conn.createChannel(false)) {

                channel.queueDeclare(RabbitMQConfig.QUEUE_NAME, true, false, false, null);
                channel.basicQos(1);

                System.out.println("👷 Consumer-" + id + " started.");

                DeliverCallback callback = (tag, delivery) -> {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    System.out.println("🔧 Consumer-" + id + " processing: " + message);
                    try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // 模擬慢速處理
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                };

                channel.basicConsume(RabbitMQConfig.QUEUE_NAME, false, callback, tag -> {});

                // 保持這個 consumer 存活
                while (true) {
                    Thread.sleep(1000);
                }

            } catch (Exception e) {
                System.err.println("❌ Consumer-" + id + " error: " + e.getMessage());
            }
        });
    }
}
