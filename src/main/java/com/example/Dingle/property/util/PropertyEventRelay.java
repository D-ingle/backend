package com.example.Dingle.property.util;

import com.example.Dingle.global.config.RabbitConfig;
import com.example.Dingle.property.dto.PropertyCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PropertyEventRelay {

    private final RabbitTemplate rabbitTemplate;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(PropertyCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                event
        );
    }
}
