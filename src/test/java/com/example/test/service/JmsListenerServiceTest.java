package com.example.test.service;

import com.example.test.entity.Result;
import com.example.test.repository.ResultRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class JmsListenerServiceTest {
    @Mock
    private ResultRepository resultRepository;

    @InjectMocks
    private JmsListenerService jmsListenerService;

    @Test
    public void testReceiveMessage() {
        String message = "{\"id\":1,\"number\":42}";
        jmsListenerService.receiveMessage(message);
        Integer id = 1;
        Integer number = 42;
        Integer multipliedNumber = number * 2;
        Result expectedResult = new Result();
        expectedResult.setId(id);
        expectedResult.setMultipliedNumber(multipliedNumber);
        verify(resultRepository, times(1)).save(refEq(expectedResult));
    }
}