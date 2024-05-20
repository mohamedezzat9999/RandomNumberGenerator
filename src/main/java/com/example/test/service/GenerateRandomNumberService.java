package com.example.test.service;


import com.example.test.entity.RandomNumber;
import com.example.test.repository.RandomNumberRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
    public class GenerateRandomNumberService {
    private final RandomNumberRepository randomNumberRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final JmsTemplate jmsTemplate;;
    private final Random random = new Random();

    @Scheduled(cron = "*/10 * * * * *")
    public void generateAndSaveRandomNumber() {
        Integer randomNumber = random.nextInt(100);
        RandomNumber obj = new RandomNumber();
        obj.setNumber(randomNumber);
        randomNumberRepository.save(obj);

        String jsonString = "{\"id\":" + obj.getId() + ",\"number\":" + randomNumber + "}";
        jmsTemplate.convertAndSend("random-numbers", jsonString);
        System.out.println("Generated and saved random number: " + randomNumber);
    }

}
