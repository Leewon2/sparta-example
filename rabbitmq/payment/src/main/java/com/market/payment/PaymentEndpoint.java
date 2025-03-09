package com.market.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentEndpoint {
    @Value("${spring.application.name}")
    private String appName;

    // 큐를 바라보도록 리스너를 만듦
    @RabbitListener(queues="${message.queue.payment}")
    public void receiveMessage(String orderId) {
        log.info("{} received orderId: {}", appName, orderId);
    }
}
