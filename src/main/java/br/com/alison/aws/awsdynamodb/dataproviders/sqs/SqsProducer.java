package br.com.alison.aws.awsdynamodb.dataproviders.sqs;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SqsProducer {

    private final QueueMessagingTemplate messagingTemplate;

    public void send(String topicName, Object message) {
        messagingTemplate.convertAndSend(topicName, message);
    }
}
