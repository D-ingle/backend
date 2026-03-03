package com.example.Dingle.property.util;

import com.example.Dingle.global.config.RabbitConfig;
import com.example.Dingle.property.dto.PropertyCreatedEvent;
import com.example.Dingle.property.service.PropertyAsyncOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropertyCreatedConsumer {
    private final PropertyAsyncOrchestrator orchestrator;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void handle(PropertyCreatedEvent event) {
        orchestrator.process(event.getPropertyId());
    }
}
