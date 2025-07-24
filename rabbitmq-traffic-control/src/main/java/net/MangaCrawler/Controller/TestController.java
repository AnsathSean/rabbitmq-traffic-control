package net.MangaCrawler.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import net.MangaCrawler.Service.DynamicConsumer;
import net.MangaCrawler.Service.ProducerService;

@RestController
public class TestController {

    @Autowired
    private ProducerService producer;

    @Autowired
    private DynamicConsumer dynamicConsumer;

    @GetMapping("/send/{msg}")
    public String send(@PathVariable String msg) {
        producer.send(msg);
        return "Sent: " + msg;
    }

    @PostMapping("/consumer/add")
    public String addConsumer() {
        dynamicConsumer.addNewConsumer();
        return "New Consumer started.";
    }
}
