package com.example.test.service;

import com.example.test.entity.Result;
import com.example.test.repository.ResultRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JmsListenerService {
    private final ResultRepository resultRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @JmsListener(destination = "random-numbers")
    public void receiveMessage(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            Integer id = jsonNode.get("id").asInt();
            Integer number = jsonNode.get("number").asInt();
            Integer multipliedNumber = number * 2;

            Result result = new Result();
            result.setId(id);
            result.setMultipliedNumber(multipliedNumber);
            resultRepository.save(result);

            System.out.println("Processed message: " + message);
            System.out.println("Saved result: " + multipliedNumber);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
