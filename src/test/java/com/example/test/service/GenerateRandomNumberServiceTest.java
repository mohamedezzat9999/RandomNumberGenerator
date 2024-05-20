package com.example.test.service;

import com.example.test.entity.RandomNumber;
import com.example.test.repository.RandomNumberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.kafka.core.KafkaTemplate;

import java.lang.reflect.Field;
import java.util.Random;

import static org.assertj.core.api.Fail.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenerateRandomNumberServiceTest {
    @Mock
    private RandomNumberRepository randomNumberRepository;

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private GenerateRandomNumberService generateRandomNumberService;

    @Test
    public void testGenerateAndSaveRandomNumber() {
        int randomNumber = 42;

        Random mockedRandom = Mockito.mock(Random.class);
        Mockito.when(mockedRandom.nextInt(Mockito.anyInt())).thenReturn(randomNumber);

        try {
            Field randomField = generateRandomNumberService.getClass().getDeclaredField("random");
            randomField.setAccessible(true);
            randomField.set(generateRandomNumberService, mockedRandom);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set the random field in GenerateRandomNumberService");
        }

        generateRandomNumberService.generateAndSaveRandomNumber();

        RandomNumber expectedRandomNumber = new RandomNumber();
        expectedRandomNumber.setNumber(randomNumber);
        verify(randomNumberRepository, times(1)).save(refEq(expectedRandomNumber));
    }
}