package com.project.api_cotacao.producer;

import com.project.api_cotacao.config.rabbitMq.RabbitMQConfig;
import com.project.api_cotacao.producer.dtos.EmailMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;

    public EmailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String emailUser, String code, Double bid) {

        String message = "A moeda " + code +" esta com a cotação abaixo de: " + bid;

        EmailMessage emailMessage = new EmailMessage(emailUser, code, message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, emailMessage);
        System.out.println("Mensagem do email: "+emailUser+" enviada para a fila");
    }
}
